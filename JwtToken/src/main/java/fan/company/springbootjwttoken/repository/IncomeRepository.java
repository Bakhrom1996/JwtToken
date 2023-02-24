package fan.company.springbootjwttoken.repository;

import fan.company.springbootjwttoken.entity.Card;
import fan.company.springbootjwttoken.entity.Income;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IncomeRepository extends JpaRepository<Income, Long> {
    List<Income> findAllByToCardId (Card card);
}
