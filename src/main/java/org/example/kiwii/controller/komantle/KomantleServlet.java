package org.example.kiwii.controller.komantle;

import org.apache.ibatis.session.SqlSessionFactory;
import org.example.kiwii.dao.komantle.KomantleTrialDAO;
import org.example.kiwii.mybatis.MyBatisSessionFactory;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/komantle")
public class KomantleServlet extends HttpServlet {

    private SqlSessionFactory sqlSessionFactory;
    private KomantleTrialDAO komantleTrialDAO;

    @Override
    public void init() {
        sqlSessionFactory = MyBatisSessionFactory.getSqlSessionFactory();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

    }
}
