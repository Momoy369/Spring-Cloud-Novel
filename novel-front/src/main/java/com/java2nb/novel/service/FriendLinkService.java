package com.java2nb.novel.service;


import com.java2nb.novel.entity.FriendLink;

import java.util.List;

/**
 * @author 11797
 */
public interface FriendLinkService {

    /**
     * Query Homepage Link Persahabatan
     * @return set
     * */
    List<FriendLink> listIndexLink();
}
