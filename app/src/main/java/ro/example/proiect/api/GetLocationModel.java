package ro.example.proiect.api;

import java.util.List;

public class GetLocationModel {

    String term;
    Integer moresuggestions;
    Boolean autoSuggestInstance;
    String trackingID;
    Boolean misspellingfallback;
    List<LocationModel> suggestions;

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public Integer getMoresuggestions() {
        return moresuggestions;
    }

    public void setMoresuggestions(Integer moresuggestions) {
        this.moresuggestions = moresuggestions;
    }

    public Boolean getAutoSuggestInstance() {
        return autoSuggestInstance;
    }

    public void setAutoSuggestInstance(Boolean autoSuggestInstance) {
        this.autoSuggestInstance = autoSuggestInstance;
    }

    public String getTrackingID() {
        return trackingID;
    }

    public void setTrackingID(String trackingID) {
        this.trackingID = trackingID;
    }

    public Boolean getMisspellingfallback() {
        return misspellingfallback;
    }

    public void setMisspellingfallback(Boolean misspellingfallback) {
        this.misspellingfallback = misspellingfallback;
    }

    public List<LocationModel> getSuggestions() {
        return suggestions;
    }

    public void setSuggestions(List<LocationModel> suggestions) {
        this.suggestions = suggestions;
    }
}
