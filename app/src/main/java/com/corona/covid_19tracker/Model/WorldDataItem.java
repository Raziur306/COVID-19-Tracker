package com.corona.covid_19tracker.Model;

public class WorldDataItem {
    private String flagUrl;
    private String countryName;
    private long updateTime, totalCases, todayCases, totalDeaths, todayDeaths, totalRecovered, todayRecovered, active, tests, totalPopulation,ID;

    public WorldDataItem(String flagUrl, String countryName, long updateTime, long totalCases, long todayCases, long totalDeaths, long todayDeaths, long totalRecovered, long todayRecovered, long active, long tests, long totalPopulation,long ID) {
        this.flagUrl = flagUrl;
        this.countryName = countryName;
        this.updateTime = updateTime;
        this.totalCases = totalCases;
        this.todayCases = todayCases;
        this.totalDeaths = totalDeaths;
        this.todayDeaths = todayDeaths;
        this.totalRecovered = totalRecovered;
        this.todayRecovered = todayRecovered;
        this.active = active;
        this.tests = tests;
        this.totalPopulation = totalPopulation;
        this.ID = ID;
    }

    public String getFlagUrl() {
        return flagUrl;
    }

    public String getCountryName() {
        return countryName;
    }

    public long getUpdateTime() {
        return updateTime;
    }

    public long getTotalCases() {
        return totalCases;
    }

    public long getTodayCases() {
        return todayCases;
    }

    public long getTotalDeaths() {
        return totalDeaths;
    }

    public long getTodayDeaths() {
        return todayDeaths;
    }

    public long getTotalRecovered() {
        return totalRecovered;
    }

    public long getTodayRecovered() {
        return todayRecovered;
    }

    public long getActive() {
        return active;
    }

    public long getTests() {
        return tests;
    }

    public long getTotalPopulation() {
        return totalPopulation;
    }

    public long getID() {
        return ID;
    }
}
