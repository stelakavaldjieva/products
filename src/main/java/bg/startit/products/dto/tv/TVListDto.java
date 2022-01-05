package bg.startit.products.dto.tv;

import java.util.List;

/**
 * DTO for list of tv's
 */
public class TVListDto
{

  private Integer         pageNumber;
  private Integer         pageSize;
  private Integer         count;
  private Integer         total;
  private List<TVDto> content;

  public TVListDto(Integer pageNumber, Integer pageSize, Integer count, Integer total,
                   List<TVDto> content)
  {
    this.pageNumber = pageNumber;
    this.pageSize = pageSize;
    this.count = count;
    this.total = total;
    this.content = content;
  }

  public Integer getPageNumber()
  {
    return pageNumber;
  }

  public void setPageNumber(Integer pageNumber)
  {
    this.pageNumber = pageNumber;
  }

  public Integer getPageSize()
  {
    return pageSize;
  }

  public void setPageSize(Integer pageSize)
  {
    this.pageSize = pageSize;
  }

  public Integer getCount()
  {
    return count;
  }

  public void setCount(Integer count)
  {
    this.count = count;
  }

  public Integer getTotal()
  {
    return total;
  }

  public void setTotal(Integer total)
  {
    this.total = total;
  }

  public List<TVDto> getContent()
  {
    return content;
  }

  public void setContent(List<TVDto> content)
  {
    this.content = content;
  }
}
