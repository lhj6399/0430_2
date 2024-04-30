package controller;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import domain.BoardVO;
import domain.PagingVO;
import handler.FileRemoveHandler;
import handler.PagingHandler;
import net.coobird.thumbnailator.Thumbnails;
import service.BoardService;
import service.BoardServiceImpl;

/**
 * Servlet implementation class BoardController
 */
@WebServlet("/brd/*")
public class BoardController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//로그 객체 생성
	private static final Logger log = LoggerFactory.getLogger(BoardController.class);
	
	// jsp에서 받은 요청 처리, 처리 결과를 다른 jsp로 보내는 역할을 하는 객체
	private RequestDispatcher rdp;
	
	//목적지 주소를 저장하는 변수
	private String destPage;
	
	// DB구문 체크 값 저장 변수
	private int isOk;
	
	// interface 생성
	private BoardService bsv;
	
	// 파일 업로드 저장 경로
	private String savePath;
	
	public BoardController() {
		// class 생성 bsv를 구현할 객체
		bsv = new BoardServiceImpl();
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 실제 처리 메서드
		log.info("잘 들어오는지 테스트...");
		System.out.println("sysout으로 찍은 로그");
		
		// 매개변수 객체의 인코딩 설정
		request.setCharacterEncoding("UTF-8"); // 요청 객체
		response.setCharacterEncoding("UTF-8"); // 응답 객체
		
		// 응답 객체 => 화면을 만들어서 응답 => jsp형식으로
		response.setContentType("text/html; charset=UTF-8"); // 응답 객체가 html 객체로 전송
		
		String uri = request.getRequestURI(); // jsp에서 오는 주소를 받는 객체
		log.info(uri); // /brd/register
		
		String path = uri.substring(uri.lastIndexOf("/")+1);
		log.info(path);
		
		switch(path) {
		case "register" :
			destPage = "/board/register.jsp";
			break;
			
		case "insert" : 
			try {
				// 파일을 업로드할 물리적인 경로 설정
				savePath = getServletContext().getRealPath("/_fileUpload");
				log.info(">>>> savePath >> {}",savePath);
				
				File fileDir = new File(savePath);
				log.info(">>>> fileDir >> {}",fileDir);
				
				DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
				// 저장할 위치를 File 객체로 지정
				fileItemFactory.setRepository(fileDir); 
				// 파일 저장을 위한 임시 메모리
				fileItemFactory.setSizeThreshold(1024*1024*3); 
				
				BoardVO bvo= new BoardVO();
				
				// multipart/form-data 형식으로 넘어온 request 객체를 다루기 쉽게 변환해주는 역할
				ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
				
				List<FileItem> itemList = fileUpload.parseRequest(request);
				for(FileItem item : itemList) {
					// title, writer, content => text / imageFile => image
					switch(item.getFieldName()) {
					case "title":
						// 변수로 분리
						String title = item.getString("utf-8");
						bvo.setTitle(title);
						break;
					case "writer":
						// 바로 가져옴
						bvo.setWriter(item.getString("utf-8"));
						break;
					case "content":
						bvo.setContent(item.getString("utf-8"));
						break;
					case "imageFile":
						// 이미지 파일 여부 체크
						if(item.getSize() > 0) {
							// 파일 이름 추출
							// getName() : 순수 파일이름 + 경로
							String fileName = item.getName()
									.substring(item.getName().lastIndexOf(File.separator)+1);
							// File.separator : 파일 경로 기호 => 운영체제마다 다를 수 있어서 자동 변환
							// 시스템의 시간을 이용하여 파일을 구분 / 시간_dog.jpg
							fileName = System.currentTimeMillis()+"_"+fileName;
											
							File uploadFilePath = new File(fileDir+File.separator+fileName);
							log.info(">>>> uploadFilePath >> {}",uploadFilePath);
							
							// 저장
							try {
								item.write(uploadFilePath); // 객체를 디스크에 저장
								bvo.setImageFile(fileName); // bvo에 저장할 값 (BD에 저장할 값)
								
								// 썸네일 작업 : 리스트 페이지에서 트레픽 과다 사용 방지
								Thumbnails.of(uploadFilePath)
								.size(75, 75)
								.toFile(new File(fileDir+File.separator+"_th_"+fileName));
							} catch (Exception e) {
								// TODO: handle exception
								log.info(">>>> file writer on disk error");
								e.printStackTrace();
							}
						}
						break;
					}
					
					
				}
				log.info(">>>> bvo >>{}",bvo);
				int isOk = bsv.register(bvo);
				log.info("insert " + (isOk>0?"성공 ":"실패 => ")+isOk);
				destPage = "/index.jsp";
//				 첨부파일이 없을 때
				// jsp 화면에서 보내온 파라미터 저장
//				String title = request.getParameter("title");
//				String writer = request.getParameter("writer");
//				String content = request.getParameter("content");
//				
//				BoardVO bvo = new BoardVO(title, writer, content);
//				log.info("insert 객체 >> {}",bvo);
//				int isOk = bsv.register(bvo);
//				log.info("insert " + (isOk>0?"성공 ":"실패 => ")+isOk);
//				
//				destPage = "/index.jsp";
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			break;
			
		case "list" :
			try {
				// index에서 list버튼을 클릭하면
				// 컨트롤러에서 DB의 전체 리스트를 요청
				// 전체 리스트를 list.jsp로 가져가서 뿌리기
				
				// Paging 객체 설정
				
				PagingVO pgvo = new PagingVO(); // 1p / 10개 / 0번지 / type / keyword
				
				if(request.getParameter("pageNo") != null) {
					int pageNo = Integer.parseInt(request.getParameter("pageNo"));
					int qty = Integer.parseInt(request.getParameter("qty"));
					String type = request.getParameter("type");
					String keyword = request.getParameter("keyword");
					
					pgvo = new PagingVO(pageNo, qty, type, keyword);
					
				}
				
//				List<BoardVO> list = bsv.getList();
				
				// paging을 반영한 리스트 추출
				List<BoardVO> list = bsv.getList(pgvo);
				
//				log.info("list >> {}", list);
//				totalCount DB에서 검색해서 가져오기
				int totalCount = bsv.getTotal(pgvo);
				log.info(">>> totalCount {}", totalCount);
				PagingHandler ph = new PagingHandler(pgvo, totalCount);
				log.info(">>> ph {}", ph);
				request.setAttribute("list", list);
				request.setAttribute("ph", ph);
				destPage = "/board/list.jsp";
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			break;
			
		case "detail":
			try {
				int bno = Integer.parseInt(request.getParameter("bno"));
				BoardVO bvo = bsv.getDetail(bno);
				log.info("detail bvo >> {}", bvo);
				request.setAttribute("bvo", bvo);
				destPage = "/board/detail.jsp";
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			break;
		
		case "modify" :
			try {
				int bno = Integer.parseInt(request.getParameter("bno"));
				BoardVO bvo = bsv.getDetail(bno);
				log.info("detail bvo >> {}",bvo);
				request.setAttribute("bvo", bvo);
				destPage = "/board/modify.jsp";
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			break;
		
		case "update" :
			try {
				savePath = getServletContext().getRealPath("/_fileUpload");
				File fileDir = new File(savePath);
				
				DiskFileItemFactory fileItemFactory = new DiskFileItemFactory();
				fileItemFactory.setRepository(fileDir);
				fileItemFactory.setSizeThreshold(1024*1023*3);
				
				BoardVO bvo = new BoardVO();
				
				ServletFileUpload fileUpload = new ServletFileUpload(fileItemFactory);
				
				List<FileItem> itemList = fileUpload.parseRequest(request);
				
				String old_file = null;
				
				for(FileItem item : itemList) {
					switch(item.getFieldName()) {
					case "bno":
						bvo.setBno(Integer.parseInt(item.getString("utf-8")));
						break;
					case "title":
						bvo.setTitle(item.getString("utf-8"));
						break;
					case "content":
						bvo.setContent(item.getString("utf-8"));
						break;
					case "imageFile":
						// 기존 파일 => 있을 수도 있고, 없을 수도 있다.
						old_file = item.getString("utf-8");
						break;
					case "newFile":
						// 새로 추가된 파일 => 있을 수도 있고, 없을 수도 있다.
						if(item.getSize() > 0) {
							// 새로운 등록 파일이 있다면...
							if(old_file != null) {
								// old_file 삭제 작업
								// fileRemoveHandler 통해서 파일 삭제 작업 진행
								FileRemoveHandler fileHandler = new FileRemoveHandler();
								isOk = fileHandler.deleteFile(path, old_file);
							}
							
							// 새로운 파일 등록 작업
							String fileName = item.getName().substring(item.getName().lastIndexOf(File.separator)+1);
							log.info(">>>> new file Name >> {}", fileName);
							
							fileName = System.currentTimeMillis()+"_"+fileName;
							File uploadFilePath = new File(fileDir+File.separator+fileName);
							
							try {
								item.write(uploadFilePath);
								bvo.setImageFile(fileName);
								
								Thumbnails.of(uploadFilePath)
								.size(75, 75)
								.toFile(new File(fileDir+File.separator+"_th_"+fileName));
							} catch (Exception e) {
								// TODO: handle exception
								log.info("Filte Writer Update Error");
								e.printStackTrace();
							}
						}else {
							// 기존 파일은 있찌만, 새로운 이미지 파일이 없다면...
							bvo.setImageFile(old_file); // 기존 객체 bvo에 담기
						}
						break;
					}
				}
				int isOk = bsv.update(bvo);
				log.info("update " + (isOk>0?"성공":"실패 => ") + isOk );
				destPage = "list"; // 내부 list케이스를 한번 타고 실행
				
				// 이미지 파일 없을 때
//				//update : bno, title, content
//				int bno = Integer.parseInt(request.getParameter("bno"));
//				String title = request.getParameter("title");
//				String content = request.getParameter("content");
//				
//				BoardVO bvo = new BoardVO(bno, title, content);
//				
//				int isOk = bsv.update(bvo);
//				log.info("update " + (isOk>0?"성공":"실패 => ") + isOk );
//				destPage = "list"; // 내부 list케이스를 한번 타고 실행
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			break;
			
		case "remove" :
			try {
				savePath = getServletContext().getRealPath("/_fileUpload");
				// delete : delete from board where bno = #{bno}
				int bno = Integer.parseInt(request.getParameter("bno"));
				// 댓글, 파일도 같이 삭제
				// bno 주고 파일이름 찾아오기 (DB에서)
				// 찾아온 이름이 있다면... FileRemoveHandler를 이용하여 삭제
				
				// bno로 댓글 삭제 요청 => serviceImpl에서 cdao에게 요청
				String imageFileName = bsv.getFileName(bno);
				log.info(">>>> imageFileName >> {}",imageFileName);
				
				int isDel = 0;
				if(imageFileName != null) {
					FileRemoveHandler fh = new FileRemoveHandler();
					isDel = fh.deleteFile(savePath, imageFileName);
				}
				
				// bno로 댓글 / 게시글 삭제 요청 => serviceImpl에서 cdao에게 요청
				int isOk = bsv.delete(bno);
				log.info("delete " + (isOk>0?"성공 ":"실패 => ")+isOk);
				destPage ="list";
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			break;
		}
		// 목적지 주소(destPage)로 데이터를 전달 (RequestDispatcher)
		rdp = request.getRequestDispatcher(destPage);
		// 요청에 필요한 객체를 가지고 destPage에 적힌 경로로 이동
		rdp.forward(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		service(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		service(request, response);
	}

}
