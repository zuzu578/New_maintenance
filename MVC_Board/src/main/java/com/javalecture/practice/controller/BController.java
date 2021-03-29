package com.javalecture.practice.controller;

import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.javalecture.practice.BDao.IDao;
import com.javalecture.practice.BDto.BDto;

@Controller
public class BController {
	//==> mybatis Libaray ���� SqlSession import<==//
	@Autowired
	private SqlSession sqlSession;
	
	@RequestMapping("/list")
	
	public String list(Model model) {
		IDao dao = sqlSession.getMapper(IDao.class);
		System.out.println("debug1");
		ArrayList<BDto>dtos = dao.list();
		System.out.println("debug2");
		model.addAttribute("dtos",dtos);
		System.out.println("debug3");
		return "list";
		
	}
	/*
	@RequestMapping("/login")
	public String doLogin() {
		return "login";
	}
	*/
	
	@RequestMapping("/write_view")
	public String write_view(HttpServletRequest req, Model model) {
		/*
		HttpSession session = req.getSession();
		if(session.getAttribute("uid") == null || session.getAttribute("uid").equals("")){
			return "login";
		}
		*/
		return "write_view";
	}
	@RequestMapping(value="/write",method=RequestMethod.POST)
	public String write(HttpServletRequest req, Model model) {
		//==>write_view.jsp ���� form(method = post ������� ������ ������)
		//==>getParamteter( ) �� �޾Ƽ� dao �� ������ 
		String bTitle = req.getParameter("bTitle");
		String bName = req.getParameter("bName");
		String bContent = req.getParameter("bContent");
		IDao dao = sqlSession.getMapper(IDao.class);
		dao.write(bTitle,bName,bContent);
		return "redirect:list";
		
		
	}
	//==> Ŭ���� �Խù� ���� 
	@RequestMapping("/content_view")
	public String content_view(HttpServletRequest req, Model model) {
		int bId = Integer.parseInt(req.getParameter("bId"));
		IDao dao = sqlSession.getMapper(IDao.class);
		BDto dto = dao.contentView(bId);
		dao.upHit(bId);
		model.addAttribute("content_view",dto);
		return "content_view";
	}
	
	@RequestMapping("/modify_view")
	public String modify_view(HttpServletRequest req , Model model) {
		/*
		HttpSession session = req.getSession();
		if(session.getAttribute("uid") == null || session.getAttribute("uid").equals("")){
			return "login";
		}
		*/
		int bId = Integer.parseInt(req.getParameter("bId"));
		IDao dao = sqlSession.getMapper(IDao.class);
		BDto dto = dao.contentView(bId);
		model.addAttribute("modify_view",dto);
		return "modify_view";
	}
	@RequestMapping(value="/modify",method=RequestMethod.POST)
	public String modify(HttpServletRequest req, Model model) {
		String bId = req.getParameter("bId");
		String bName = req.getParameter("bName");
		String bTitle = req.getParameter("bTitle");
		String bContent = req.getParameter("bContent");
		IDao dao = sqlSession.getMapper(IDao.class);
		dao.modify(Integer.parseInt(bId),bName,bTitle,bContent);
		return "redirect:list";
		
	}
	@RequestMapping(value="/delete")
	public String delete(HttpServletRequest req, Model model) {
		/*
		HttpSession session = req.getSession();
		if(session.getAttribute("uid") == null ||session.getAttribute("uid").equals("")) {
			return "redirect:login";
		}
		*/
		String bId = req.getParameter("bId");
		IDao dao = sqlSession.getMapper(IDao.class);
		dao.delete(Integer.parseInt(bId));
		return "redirect:list";
	}
	
	
	
	
	
						//==>�α��� , ���ǵ��<==//
	/*
	@RequestMapping(value="/member_check",method = RequestMethod.POST)
	public String doCheck(HttpServletRequest req, Model model) {
		String userid = req.getParameter("userid");
		String passcode = req.getParameter("passcode");
		HttpSession session = req.getSession();
		IDao dao = sqlSession.getMapper(IDao.class);
		int cnt = dao.memberCheck(userid,passcode);
		if(cnt == 1 ) {
			session.setAttribute("uid", userid);
		}else {
			return "redirect:login";
		}
		return "redirect:list";
	}
	@RequestMapping("/logout")
	public String doLogout(HttpServletRequest req , Model model) {
		HttpSession session = req.getSession();
		//==> �α׾ƿ��� ���������� �����ν� �α׾ƿ� ��� ���� <==//
		
		session.invalidate();
		model.addAttribute("logout", "Y");
		return "redirect:list";
	}
	*/
	
}
