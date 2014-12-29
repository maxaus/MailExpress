package com.baev.mailexpress.service;

import com.baev.mailexpress.domain.Folder;

import java.util.List;

/**
 * Service class that contains business logic for managing folders.
 *
 * @author Maxim Baev
 */
public interface FolderService {

    /**
     * Retrieves all folders.
     *
     * @return Collection of folders
     */
    List<Folder> findAll();

    /**
     * Retrieves folder by name.
     *
     * @param name folder name
     * @return Found folder
     */
    Folder findByName(String name);

    /**
     * Persists folder.
     *
     * @param folder folder
     * @return Saved folder
     */
    Folder save(Folder folder);

    /**
     * Updates folder.
     *
     * @param folder folder
     * @return Updated folder
     */
    Folder update(Folder folder);

    /**
     * Removes folder.
     *
     * @param folderId folder ID
     */
    void delete(Long folderId);
}
