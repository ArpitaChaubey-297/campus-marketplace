package com.arpita.campusmarketplace.dto;

public class DashboardDTO {

    private long totalUsers;
    private long totalProducts;
    private long totalRequests;
    private long soldProducts;
    private long availableProducts;

    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getTotalProducts() {
        return totalProducts;
    }

    public void setTotalProducts(long totalProducts) {
        this.totalProducts = totalProducts;
    }

    public long getTotalRequests() {
        return totalRequests;
    }

    public void setTotalRequests(long totalRequests) {
        this.totalRequests = totalRequests;
    }

    public long getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(long soldProducts) {
        this.soldProducts = soldProducts;
    }

    public long getAvailableProducts(){
        return availableProducts;
    }

    public void setAvailableProducts(long availableProducts){
        this.availableProducts=availableProducts;
    }
}