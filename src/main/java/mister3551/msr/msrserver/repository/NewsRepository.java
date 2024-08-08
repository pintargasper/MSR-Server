package mister3551.msr.msrserver.repository;

import mister3551.msr.msrserver.entity.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    News findByTitle(String title);
}