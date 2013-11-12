package com.noveogroup.mailexpress.service.impl;

import com.noveogroup.mailexpress.dao.FolderDao;
import com.noveogroup.mailexpress.domain.Folder;
import com.noveogroup.mailexpress.service.FolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Maxim Baev
 */
@Service("folderService")
public class FolderServiceImpl implements FolderService {

    @Autowired
    private FolderDao folderDao;

    @Override
    @Transactional(readOnly = true)
    public List<Folder> findAll() {
        return folderDao.findAll();
    }
}
