package com.example.fighteam.post.domain.repository;


import com.example.fighteam.post.domain.dto.*;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class CommentJdbc implements  CommentRepository{
    private final  DataSource dataSource;

    public CommentJdbc(DataSource dataSource){this.dataSource =dataSource;}

    private Connection conn;
    private PreparedStatement pstmt;
    private ResultSet rs;

    private Statement stmt;

    @Override
    public Long saveComment(CreateCommentDto createCommentDto) {
        String sql = "insert into comment(user_id, post_id, comment_content, comment_date) values(?,?,?, now())";
        Long result = null;

        try {
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setLong(1, createCommentDto.getUser_id());
            pstmt.setLong(2, createCommentDto.getPost_id());
            pstmt.setString(3, createCommentDto.getComment());
            pstmt.executeUpdate();
            rs = pstmt.getGeneratedKeys();
            if(rs.next()){
                result = rs.getLong(1);
            }else{
                throw new SQLException("댓글 생성 실패");
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            close(conn, pstmt, rs);
        }
        return createCommentDto.getPost_id();
    }

    @Override
    public List<GetCommentDto> findbyPostid(Long post_id) {
        String sql = "select comment_id, c.user_id, post_id, comment_content, comment_date, u.name " +
                "from comment c inner join users u on u.user_id = c.user_id where post_id = ? order by comment_date desc";
        List<GetCommentDto> getCommentDto = new ArrayList<>();
        try{
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,post_id);
            rs = pstmt.executeQuery();
            while (rs.next()){
                GetCommentDto g = new GetCommentDto();
                g.setComment_id(rs.getLong("comment_id"));
                g.setPost_id(rs.getLong("post_id"));
                g.setUser_id(rs.getLong("user_id"));
                g.setComment(rs.getString("comment_content"));
                g.setCommentCreatedTime(rs.getDate("comment_date"));
                g.setUser_name(rs.getString("name"));
                getCommentDto.add(g);
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            close(conn, pstmt, rs);
        }

        return getCommentDto;
    }

    @Override
    public Boolean removeComment(Long commentId) {
        Boolean result = false;
        int row = 0;
        String sql = "delete from comment where comment_id = ?";
        try{
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1,commentId);
            row = pstmt.executeUpdate();
            if (row > 0){
                result = true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            close(conn, pstmt, rs);
        }

        return result;
    }

    @Override
    public Boolean updateComment(Long commentId, String comment) {
        Boolean result = false;
        int row = 0;
        String sql = "update comment set comment_content = ? where comment_id = ?";
        try{
            conn = dataSource.getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1,comment);
            pstmt.setLong(2,commentId);
            row = pstmt.executeUpdate();
            if (row > 0){
                result = true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }finally {
            close(conn, pstmt, rs);
        }

        return result;
    }

    private Connection getConnection(){
        return DataSourceUtils.getConnection(dataSource);
    }
    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs){
        try {
            if (rs != null) {
                rs.close();
            }
        }
        catch(SQLException e) {e.printStackTrace();}

        try{
            if (pstmt != null){
                pstmt.close();
            }
        }catch(SQLException e) {e.printStackTrace();}

        try{
            if (conn != null){
                conn.close();
            }
        }catch(SQLException e) {e.printStackTrace();}
    }

    private void close(Connection conn) throws SQLException{
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}
