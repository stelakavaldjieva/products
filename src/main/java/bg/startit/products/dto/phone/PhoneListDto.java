package bg.startit.products.dto.phone;

import java.util.List;

/**
 * DTO for list of phones
 */
public class PhoneListDto
{

  private Integer         pageNumber;
  private Integer         pageSize;
  private Integer         count;
  private Integer         total;
  private List<PhoneDto> content;

  public PhoneListDto(Integer pageNumber, Integer pageSize, Integer count, Integer total,
                      List<PhoneDto> content)
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

  public List<PhoneDto> getContent()
  {
    return content;
  }

  public void setContent(List<PhoneDto> content)
  {
    this.content = content;
  }
}
