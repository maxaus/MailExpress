package com.noveogroup.mailexpress.service.impl;

import com.noveogroup.mailexpress.dao.FolderDao;
import com.noveogroup.mailexpress.domain.Folder;
import com.noveogroup.mailexpress.service.FolderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementation of the {@link FolderService}.
 *
 * @author Maxim Baev
 */
@Service("folderService")
public class FolderServiceImpl implements FolderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FolderServiceImpl.class);

    @Autowired
    private FolderDao folderDao;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public List<Folder> findAll() {
        LOGGER.info("Retrieving all folders.");
        return folderDao.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Folder findByName(final String name) {
        LOGGER.info("Retrieving folder by name : {}", name);
        return folderDao.findByName(name);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Folder save(final Folder folder) {
        LOGGER.info("Saving new folder");
        return folderDao.save(folder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public Folder update(final Folder folder) {
        LOGGER.info("Updating folder with ID = {}", folder.getId());
        return folderDao.save(folder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void delete(final Long folderId) {
        LOGGER.info("Removing folder with ID = {}", folderId);
        folderDao.delete(folderId);
    }
}
