package org.advancedsr.services;

import org.advancedsr.dtos.UserStorageDTO;
import org.advancedsr.entities.User;
import org.advancedsr.entities.UserStorage;
import org.advancedsr.repositories.UserRepository;
import org.advancedsr.repositories.UserStorageRepository;
import org.advancedsr.mappers.UserStorageMapper;
import org.postgresql.PGConnection;
import org.postgresql.largeobject.LargeObject;
import org.postgresql.largeobject.LargeObjectManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;
import javax.sql.DataSource;
import javax.sql.rowset.serial.SerialBlob;

@Service
public class UserStorageService {


    private final  UserStorageRepository userStorageRepository;

    private final UserRepository userRepository;

    private final DataSource dataSource;

    @Autowired
    public UserStorageService(UserStorageRepository userStorageRepository, UserRepository userRepository, UserService userService, DataSource dataSource) {
        this.userStorageRepository = userStorageRepository;
        this.userRepository = userRepository;
        this.dataSource = dataSource;
    }


    @Transactional(readOnly = true)
    public UserStorageDTO getUserStorage(Long id) {
        return userStorageRepository.findById(id)
                .map(UserStorageMapper.INSTANCE::userStoragetouserStorageDTO)
                .orElseThrow(() -> new IllegalArgumentException("UserStorage not found with id: " + id));
    }

    @Transactional
    public UserStorageDTO saveUserStorage(MultipartFile file, String username) throws SQLException, IOException {
        UserStorage userStorage = new UserStorage();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found with username: " + username));
        userStorage.setUser(user);

        // Handling the file storage into PostgreSQL Large Object
        long oid = createLargeObject(file.getInputStream());
        userStorage.setLargeObject(oid); // Store the OID

        userStorage = userStorageRepository.save(userStorage);
        return UserStorageMapper.INSTANCE.userStoragetouserStorageDTO(userStorage);
    }

    private long createLargeObject(InputStream inputStream) throws SQLException, IOException {
        try (Connection conn = dataSource.getConnection()) {
            conn.setAutoCommit(false);
            PGConnection pgConn = conn.unwrap(PGConnection.class);
            LargeObjectManager largeObject = pgConn.getLargeObjectAPI();

            long oid = largeObject.createLO(LargeObjectManager.READ | LargeObjectManager.WRITE);
            try (LargeObject obj = largeObject.open(oid, LargeObjectManager.WRITE)) {
                byte[] buffer = new byte[2048];
                int s;
                while ((s = inputStream.read(buffer)) > 0) {
                    obj.write(buffer, 0, s);
                }
            }
            conn.commit();
            return oid;
        }
    }



}

