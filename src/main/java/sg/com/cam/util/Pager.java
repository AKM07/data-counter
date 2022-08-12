package sg.com.cam.util;


import java.util.Objects;

public class Pager {

    private Integer pageNumber;
    private Integer pageSize;
    private String searchCriteria;
    private String sortField;
    private String sortOrder;

    public static Pager newInstance(Integer pageNumber, Integer pageSize) {
        Pager object = new Pager();
        object.setPageNumber(pageNumber);
        object.setPageSize(pageSize);
        return object;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getSearchCriteria() {
        return searchCriteria;
    }

    public void setSearchCriteria(String searchCriteria) {
        this.searchCriteria = searchCriteria;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pager pager = (Pager) o;
        return Objects.equals(pageNumber, pager.pageNumber) && Objects.equals(pageSize, pager.pageSize) && Objects.equals(searchCriteria, pager.searchCriteria) && Objects.equals(sortField, pager.sortField) && Objects.equals(sortOrder, pager.sortOrder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pageNumber, pageSize, searchCriteria, sortField, sortOrder);
    }
}