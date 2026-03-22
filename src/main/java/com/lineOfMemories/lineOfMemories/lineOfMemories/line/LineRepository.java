package com.lineOfMemories.lineOfMemories.lineOfMemories.line;

import com.lineOfMemories.lineOfMemories.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LineRepository extends JpaRepository<Line, Long> {
    Optional<Line> findByTitleAndUser(String title, User user);

    List<Line> findAllByUser(User user);

    Optional<Line> findByIdAndUser(Long id, User user);
}
