package org.example.kiwii.dao;

import org.apache.ibatis.session.SqlSession;

public class WordleWordDAO {
    SqlSession sqlSession;

    public WordleWordDAO(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public int selectWordleWordByCharacters(String characters) {
        return sqlSession.selectOne("wordlewords.selectWordleWordByCharacters", characters);
    }
}
