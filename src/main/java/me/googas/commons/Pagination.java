package me.googas.commons;

import java.util.ArrayList;
import java.util.List;
import org.jetbrains.annotations.NotNull;

/**
 * This object helps to create pagination for certain type of objects. It is done using an {@link
 * List} of the objects and those will be paginated using their index.
 *
 * @param <O> the type of object that is being paginated
 */
public class Pagination<O> {

  /** The list of objects to paginate */
  @NotNull private final List<O> list;
  /** The limit of objects per page */
  private int limit = 10;

  /**
   * Create a new pagination object. If no limit is provided it will use a limit of 10 objects
   *
   * @param list the list of objects to paginate
   */
  public Pagination(@NotNull List<O> list) {
    this.list = list;
  }

  /**
   * Create a new pagination object
   *
   * @param list the list of objects to paginate
   * @param limit the limit of objects per page
   */
  public Pagination(@NotNull List<O> list, int limit) {
    this.list = list;
    this.limit = limit;
  }

  /**
   * Get the max page number according to some limit. The max page number will be given as follows:
   *
   * <p>max = ({@link List#size()} + limit - 1) / limit
   *
   * <p>Deprecated since there's now a given limit for the pagination. Use {@link #maxPage()}
   * instead
   *
   * @param limit the limit of elements per page
   * @return the max page number
   */
  @Deprecated
  public int maxPage(int limit) {
    return (this.list.size() + limit - 1) / limit;
  }

  /**
   * Get the max page number according to some limit. The max page number will be given as follows:
   *
   * <p>max = ({@link List#size()} + {@link #limit} - 1) / {@link #limit}
   *
   * @return the max page number
   */
  public int maxPage() {
    return (this.list.size() + this.limit - 1) / this.limit;
  }

  /**
   * Get page given the page number and a limit of elements for each page. If the page is greater or
   * lower than the min it will go to the nearest page.
   *
   * @param page the page queried to see
   * @return the page as a list of the elements inside of it
   */
  public List<O> getPage(int page) {
    if (page < 1) {
      page = 1;
    } else if (page > this.maxPage()) {
      page = this.maxPage();
    }
    List<O> newPage = new ArrayList<>();
    int skip = (page - 1) * this.limit;
    int i = skip;
    while (i < skip + this.limit && i < this.list.size()) {
      newPage.add(this.list.get(i));
      i++;
    }
    return newPage;
  }

  /**
   * Get the index of an object inside the list. It will make a for i loop and the index that
   * matches the given object is the one that is going to be returned. If the loop ends and the
   * object was not given it will return -1.
   *
   * @param o the object to get the index of
   * @return the index of the object or -1 if the list does not contain it
   */
  public int getIndex(@NotNull O o) {
    for (int i = 0; i < this.list.size(); i++) {
      if (o.equals(this.list.get(i))) {
        return i;
      }
    }
    return -1;
  }

  /**
   * Get the list that is being paginated in this pagination object. Instead of delegating use this
   * method to manipulate the list instead
   *
   * @return the list that is being paginated
   */
  @NotNull
  public List<O> getList() {
    return this.list;
  }

  /**
   * Get the limit of elements for every page
   *
   * @return the limit of elements
   */
  public int getLimit() {
    return this.limit;
  }

  /**
   * Set the limit of elements by page
   *
   * @param limit the new value of limit
   */
  public void setLimit(int limit) {
    Validate.assertTrue(limit > 0, "The limit by page cannot be lower than 0");
    this.limit = limit;
  }
}
