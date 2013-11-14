package com.noveogroup.mailexpress.service;

import com.noveogroup.mailexpress.domain.Folder;

import java.util.List;

/**
 * Service class that contains business logic for managing folders.
 *
 * @author Maxim Baev
 */
public interface FolderService {

    /**
     *
     * @return
     */
    List<Folder> findAll();

    /**
     *
     * @param name
     * @return
     */
    Folder findByName(String name);

    /**
     *
     * @param folder
     * @return
     */
    Folder save(Folder folder);

    /**
     *
     * @param folder
     * @return
     */
    Folder update(Folder folder);

    /**
     *
     * @param folder
     */
    void delete(Folder folder);
}
