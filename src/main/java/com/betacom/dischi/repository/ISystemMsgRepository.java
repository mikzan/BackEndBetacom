package com.betacom.dischi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.dischi.models.KeyID;
import com.betacom.dischi.models.SystemMsg;

public interface ISystemMsgRepository extends JpaRepository<SystemMsg, KeyID>{

}
