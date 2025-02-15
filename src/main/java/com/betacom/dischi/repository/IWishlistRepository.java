package com.betacom.dischi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.betacom.dischi.models.Cliente;
import com.betacom.dischi.models.Wishlist;

public interface IWishlistRepository extends JpaRepository<Wishlist, Integer>{

	Optional<Wishlist> findByCliente(Cliente cliente);

}
