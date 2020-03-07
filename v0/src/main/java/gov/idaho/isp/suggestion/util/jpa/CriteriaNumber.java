package gov.idaho.isp.suggestion.util.jpa;

import java.math.BigDecimal;
import java.text.Format;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Predicate;
import javax.swing.text.NumberFormatter;
import org.apache.commons.lang3.StringUtils;

public class CriteriaNumber {
  public enum SearchType {
    LESS_THAN("<") {
      @Override
      Predicate build(CriteriaBuilder cb, Expression exp, Number n1, Number n2) {
        return cb.lt(exp, n1);
      }
    },
    LESS_THAN_OR_EQUAL("<=") {
      @Override
      Predicate build(CriteriaBuilder cb, Expression exp, Number n1, Number n2) {
        return cb.le(exp, n1);
      }
    },
    EQUAL("=") {
      @Override
      Predicate build(CriteriaBuilder cb, Expression exp, Number n1, Number n2) {
        return cb.equal(exp, n1);
      }
    },
    GREATER_THAN_OR_EQUAL(">=") {
      @Override
      Predicate build(CriteriaBuilder cb, Expression exp, Number n1, Number n2) {
        return cb.ge(exp, n1);
      }
    },
    GREATER_THAN(">") {
      @Override
      Predicate build(CriteriaBuilder cb, Expression exp, Number n1, Number n2) {
        return cb.gt(exp, n1);
      }
    },
    BETWEEN("between") {
      @Override
      boolean isBuildable(Number val1, Number val2) {
        return val1 != null && val2 != null;
      }

      @Override
      Predicate build(CriteriaBuilder cb, Expression exp, Number n1, Number n2) {
        return cb.and(cb.ge(exp, n1), cb.le(exp, n2));
      }

      @Override
      String getDescription(NumberFormatter formatter, Number n1, Number n2) {
        Format format = formatter.getFormat();
        return getLabel() + " " + format.format(n1) + " and " + format.format(n2);
      }
    },
    NONE("blank") {
      @Override
      boolean isBuildable(Number val1, Number val2) {
        return true;
      }

      @Override
      Predicate build(CriteriaBuilder cb, Expression exp, Number n1, Number n2) {
        return cb.isNull(exp);
      }

      @Override
      String getDescription(NumberFormatter formatter, Number n1, Number n2) {
        return getLabel();
      }
    };

    private final String label;

    private SearchType (String label) {
      this.label = label;
    }

    public String getLabel() {
      return label;
    }

    boolean isBuildable(Number val1, Number val2) {
      return val1 != null;
    }

    abstract Predicate build(CriteriaBuilder cb, Expression exp, Number value1, Number value2);

    String getDescription(NumberFormatter formatter, Number number1, Number number2) {
      return getLabel() + " " + formatter.getFormat().format(number1);
    }
  }

  private Number number1;
  private Number number2;
  private SearchType searchType = SearchType.EQUAL;
  private NumberFormatter numberFormatter = new NumberFormatter();

  public static CriteriaNumber valueOf(String number) {
    if (!StringUtils.isNumeric(number)) {
      return null;
    }

    CriteriaNumber cn = new CriteriaNumber();
    cn.setNumber1(new BigDecimal(number));
    return cn;
  }

  public CriteriaNumber() {
  }

  public CriteriaNumber(SearchType searchType) {
    this.searchType = searchType;
  }

  public Number getNumber1() {
    return number1;
  }

  public void setNumber1(Number number1) {
    this.number1 = number1;
  }

  public Number getNumber2() {
    return number2;
  }

  public void setNumber2(Number number2) {
    this.number2 = number2;
  }

  public SearchType getSearchType() {
    return searchType;
  }

  public void setSearchType(SearchType searchType) {
    this.searchType = searchType;
  }

  public NumberFormatter getNumberFormatter() {
    return numberFormatter;
  }

  public void setNumberFormatter(NumberFormatter numberFormatter) {
    this.numberFormatter = numberFormatter;
  }

  public boolean isBuildable() {
    return searchType != null && searchType.isBuildable(number1, number2);
  }

  public Predicate constructPredicate(CriteriaBuilder cb, Expression exp) {
    return searchType.build(cb, exp, number1, number2);
  }
  
  public String getDescription() {
    return searchType.getDescription(numberFormatter, number1, number2);
  }

  @Override
  public String toString() {
    return "CriteriaNumber{" + "number1=" + number1 + ", number2=" + number2 + ", searchType=" + searchType + ", numberFormatter=" + numberFormatter + '}';
  }
}