package com.avada.kino.service;

import com.avada.kino.dao.ContactDao;
import com.avada.kino.models.Contact;
import com.avada.kino.models.Image;
import com.avada.kino.util.UploadPaths;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

import static com.avada.kino.util.UploadPaths.CONTACTS_PATH;

@Service
@RequiredArgsConstructor
public class ContactService {
    private final ContactDao dao;
    private final FileService fileService;

    public List<Contact> getAll() {
        return dao.findAll();
    }

    public Contact getById(int id) {
        return dao.getOne(id);
    }

    public void save(Contact contact, MultipartFile multipartFile) {
        if (!multipartFile.isEmpty()) {
            String name = fileService.saveFile(multipartFile, CONTACTS_PATH);
            contact.setLogo(new Image(name, File.separator + CONTACTS_PATH + File.separator + name));
        }
        dao.save(contact);
    }

    public void update(Contact contact) {
        dao.save(contact);
    }

    public void delete(int id) {
        Contact contact = getById(id);
        fileService.deleteFile(contact.getLogo().getName(), CONTACTS_PATH);
        dao.deleteById(id);
    }
}
