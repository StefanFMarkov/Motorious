package projectdefence.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import projectdefence.models.entities.LogUnit;

import java.util.List;

@Repository
public interface LogRepository extends JpaRepository<LogUnit, String> {

    @Transactional(readOnly = true)
    @Query(value = "select *  from logs as  l  order by l.date_time desc limit 500  ", nativeQuery = true)
    List<LogUnit> findAllOrderByDateTime();
}
