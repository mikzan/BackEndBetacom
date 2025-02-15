package com.betacom.dischi.DTO;

import java.util.List;

public class WishlistDTO {
    
    private Integer idWishlist;
    private List<ProdottoDTO> prodotti;
    private ClienteDTO cliente;
    
    private WishlistDTO() {
        super();
    }

    public static class Builder {
        private Integer idWishlist;
        private List<ProdottoDTO> prodotti;
        private ClienteDTO cliente;
   
        public Builder() {
        }

        public WishlistDTO build() {
            WishlistDTO wishlist = new WishlistDTO();
            wishlist.idWishlist = this.idWishlist;
            wishlist.prodotti = this.prodotti;
            wishlist.cliente = this.cliente;
            return wishlist;
        }

        public Builder idWishlist(Integer idWishlist) {
            this.idWishlist = idWishlist;
            return this;
        }

        public Builder prodotti(List<ProdottoDTO> prodotti) {
            this.prodotti = prodotti;
            return this;
        }

        public Builder cliente(ClienteDTO cliente) {
            this.cliente = cliente;
            return this;
        }
    }

    public Integer getIdWishlist() {
        return idWishlist;
    }

    public List<ProdottoDTO> getProdotti() {
        return prodotti;
    }

    public ClienteDTO getCliente() {
        return cliente;
    }

    @Override
    public String toString() {
        return "WishlistDTO [idWishlist=" + idWishlist + ", prodotti=" + prodotti + ", cliente=" + cliente + "]";
    }
}
