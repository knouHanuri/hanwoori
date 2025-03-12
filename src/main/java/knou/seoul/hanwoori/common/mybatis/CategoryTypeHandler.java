package knou.seoul.hanwoori.common.mybatis;

import knou.seoul.hanwoori.domain.post.dto.Post;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(Post.Category.class)
@MappedJdbcTypes(JdbcType.INTEGER)
public class CategoryTypeHandler extends BaseTypeHandler<Post.Category> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, Post.Category parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i,parameter.ordinal());   // enum 의 순서를 int로 저장
    }

    @Override
    public Post.Category getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int ordinal = rs.getInt(columnName);
        return Post.Category.values()[ordinal]; //숫자를 enum으로 변환
    }

    @Override
    public Post.Category getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int ordinal = rs.getInt(columnIndex);
        return Post.Category.values()[ordinal];
    }

    @Override
    public Post.Category getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int ordinal = cs.getInt(columnIndex);
        return Post.Category.values()[ordinal];
    }
}
