package com.betacom.dischi.request;

import java.util.List;

public class WishlistRequest {
    private Integer idCliente;
    private List<Integer> idProdotti;
    private Integer idWishlist;

    public WishlistRequest() {}

    
    

    public Integer getIdWishlist() {
		return idWishlist;
	}




	public void setIdWishlist(Integer idWishlist) {
		this.idWishlist = idWishlist;
	}




	public Integer getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(Integer idCliente) {
        this.idCliente = idCliente;
    }

    public List<Integer> getIdProdotti() {
        return idProdotti;
    }

    public void setIdProdotti(List<Integer> idProdotti) {
        this.idProdotti = idProdotti;
    }
}
