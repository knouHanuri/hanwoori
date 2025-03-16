package knou.seoul.hanwoori.common.mybatis;

import knou.seoul.hanwoori.domain.file.dto.File;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@MappedTypes(File.SourceKind.class)
@MappedJdbcTypes(JdbcType.INTEGER)
public class SourceKindTypeHandler extends BaseTypeHandler<File.SourceKind> {
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, File.SourceKind parameter, JdbcType jdbcType) throws SQLException {
        ps.setInt(i, parameter.ordinal());   // enum 의 순서를 int로 저장
    }

    @Override
    public File.SourceKind getNullableResult(ResultSet rs, String columnName) throws SQLException {
        int ordinal = rs.getInt(columnName);
        return File.SourceKind.values()[ordinal];
    }

    @Override
    public File.SourceKind getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        int ordinal = rs.getInt(columnIndex);
        return File.SourceKind.values()[ordinal];
    }

    @Override
    public File.SourceKind getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        int ordinal = cs.getInt(columnIndex);
        return File.SourceKind.values()[ordinal];
    }
}
