package com.baev.mailexpress.dao;

import com.baev.mailexpress.domain.Folder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Folder DAO.
 *
 * @author Maxim Baev
 */
@Repository("folderDao")
public interface FolderDao extends JpaRepository<Folder, Long> {

    /**
     * Retrieves folder by name.
     *
     * @param name folder name.
     * @return Folder with given name.
     */
    Folder findByName(final String name);
}
