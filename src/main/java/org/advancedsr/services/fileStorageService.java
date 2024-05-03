package org.advancedsr.services;

import org.postgresql.PGConnection;
import org.postgresql.largeobject.LargeObject;
import org.postgresql.largeobject.LargeObjectManager;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;


@Service
public class fileStorageService {
    private final DataSource dataSource;

    public fileStorageService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public InputStream getFileStream(Long largeObjectId) throws SQLException {
        Connection conn = dataSource.getConnection();
        LargeObjectManager lobj = ((PGConnection)conn).getLargeObjectAPI();
        LargeObject obj = lobj.open(largeObjectId, LargeObjectManager.READ);
        return obj.getInputStream();
    }


}
