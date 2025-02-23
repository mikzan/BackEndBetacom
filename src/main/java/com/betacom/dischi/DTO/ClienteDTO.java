package com.betacom.dischi.DTO;

import java.time.LocalDate;
import java.util.List;
public class ClienteDTO { // APPLICO IL PATTERN BUILDER
    // 1) ATTRIBUTI DTO
    private Integer idCliente;
    private String nome;
    private String cognome;
    private String telefono;
    private String immagineCliente;
    private String via;
    private String cap;
    private String provincia;
    private String comune;
    private LocalDate dataRegistrazione;
    private List<OrdineDTO> ordini;
    private CarrelloDTO carrello;
    private UtenteDTO utente;
    private WishlistDTO wishlist;    
    private List<RecensioneDTO> recensioni;
    // Parametri di paginazione
    private int page; 
    private int size; 
    private int totalElements; 
    private int totalPages; 
    
    // 2) COSTRUTTORE PRIVATO PER DTO (NO ISTANZE AD DI FUORI DI QUESTA CLASSE)
    private ClienteDTO() {}
    
    // INNER CLASS BUILDER PER CLIENTEDTO
    public static class Builder {
        // 1) Riprendo le variabili di oggetto DTO
        private Integer idCliente;
        private String nome;
        private String cognome;
        private String telefono;
        private String immagineCliente;        
        private String via;
        private String cap;
        private String provincia;
        private String comune;
        private LocalDate dataRegistrazione;
        private List<OrdineDTO> ordini;
        private CarrelloDTO carrello;
        private UtenteDTO utente;
        private WishlistDTO wishlist;        
        private List<RecensioneDTO> recensioni;
        private int page;
        private int size; 
        private int totalElements; 
        private int totalPages; 
        
        // 2) FAI SOLO I SET, questi devono tornare tutti Builder (il nome che abbiamo dato a innerclass che applica il pattern)
        public Builder idCliente(Integer idCliente) {
            this.idCliente = idCliente;
            return this;
        }
        public Builder nome(String nome) {
            this.nome = nome;
            return this;
        }
        public Builder cognome(String cognome) {
            this.cognome = cognome;
            return this;
        }
        public Builder telefono(String telefono) {
            this.telefono = telefono;
            return this;
        }
        public Builder immagineCliente(String immagineCliente) {
            this.immagineCliente = immagineCliente;
            return this;
        }
        public Builder ordini(List<OrdineDTO> ordini) {
            this.ordini = ordini;
            return this;
        }
        public Builder carrello(CarrelloDTO carrello) {
            this.carrello = carrello;
            return this;
        }
        public Builder utente(UtenteDTO utente) {
            this.utente = utente;
            return this;
        }
        public Builder wishlist(WishlistDTO wishlist) {
            this.wishlist = wishlist;
            return this;
        }
        public Builder recensioni(List<RecensioneDTO> recensioni) {
            this.recensioni = recensioni;
            return this;
        }
        public Builder via(String via) {
            this.via = via;
            return this;
        }
        public Builder cap(String cap) {
            this.cap = cap;
            return this;
        }
        public Builder provincia(String provincia) {
            this.provincia = provincia;
            return this;
        }
        public Builder comune(String comune) {
            this.comune = comune;
            return this;
        }
        public Builder dataRegistrazione(LocalDate dataRegistrazione) {
            this.dataRegistrazione = dataRegistrazione;
            return this;
        }
        
        // 3) Parametri di paginazione
        public Builder page(int page) {
            this.page = page;
            return this;
        }
        
        public Builder size(int size) {
            this.size = size;
            return this;
        }
        
        public Builder totalElements(int totalElements) {
            this.totalElements = totalElements;
            return this;
        }
        
        public Builder totalPages(int totalPages) {
            this.totalPages = totalPages;
            return this;
        }

        // 4) ORA FAI UN METODO BUILD PER COSTRUIRE IL DTO E RITORNARLO
        public ClienteDTO build() {
            ClienteDTO clienteDTO = new ClienteDTO();
            clienteDTO.idCliente = this.idCliente;
            clienteDTO.nome = this.nome;
            clienteDTO.cognome = this.cognome;
            clienteDTO.telefono = this.telefono;
            clienteDTO.immagineCliente = this.immagineCliente;
            clienteDTO.ordini = this.ordini;
            clienteDTO.carrello = this.carrello;
            clienteDTO.utente = this.utente;
            clienteDTO.wishlist = this.wishlist;
            clienteDTO.recensioni = this.recensioni;
            clienteDTO.cap = this.cap;
            clienteDTO.comune = this.comune;
            clienteDTO.provincia = this.provincia;
            clienteDTO.via = this.via;
            clienteDTO.dataRegistrazione = this.dataRegistrazione;
            // Imposto anche i parametri di paginazione
            clienteDTO.page = this.page;
            clienteDTO.size = this.size;
            clienteDTO.totalElements = this.totalElements;
            clienteDTO.totalPages = this.totalPages;
            return clienteDTO;
        }
    }

    // 3) Get per l'oggetto DTO, immutabile all'esterno, è consentita solo la visualizzazione
    public Integer getIdCliente() {
        return idCliente;
    }
    public String getNome() {
        return nome;
    }
    public String getCognome() {
        return cognome;
    }
    public String getTelefono() {
        return telefono;
    }
    public String getImmagineCliente() {
        return immagineCliente;
    }
    public String getVia() {
        return via;
    }
    public String getCap() {
        return cap;
    }
    public String getProvincia() {
        return provincia;
    }
    public String getComune() {
        return comune;
    }
    public List<OrdineDTO> getOrdini() {
        return ordini;
    }
    public CarrelloDTO getCarrello() {
        return carrello;
    }
    public UtenteDTO getUtente() {
        return utente;
    }
    public WishlistDTO getWishlist() {
        return wishlist;
    }
    public List<RecensioneDTO> getRecensioni() {
        return recensioni;
    }
    public LocalDate getDataRegistrazione() {
        return dataRegistrazione;
    }

    // Parametri di paginazione
    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }
}
