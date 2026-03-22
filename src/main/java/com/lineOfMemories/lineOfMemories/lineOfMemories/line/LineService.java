package com.lineOfMemories.lineOfMemories.lineOfMemories.line;

import com.lineOfMemories.lineOfMemories.mapper.EntityMapper;
import com.lineOfMemories.lineOfMemories.user.User;
import com.lineOfMemories.lineOfMemories.user.UserRepository;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class LineService {
    private final LineRepository lineRepository;
    private final UserRepository userRepository;
    private final EntityMapper entityMapper;

    public LineService(LineRepository lineRepository, UserRepository userRepository, EntityMapper entityMapper) {
        this.lineRepository = lineRepository;
        this.userRepository = userRepository;
        this.entityMapper = entityMapper;
    }

    public void createLine(String title, Principal principal) {
        User user = findUserByPrincipal(principal);
        if (lineRepository.findByTitleAndUser(title, user).isPresent()) {
            throw new RuntimeException("This title already exist: " + title);
        }
        Line line = new Line();

        line.setTitle(title);
        line.setUser(user);
        line.setVisibility(Visibility.PUBLIC);

        lineRepository.save(line);
    }

    public void updateTitleLine(Long lineId, String title, Principal principal) {
        User user = findUserByPrincipal(principal);
        Line lineToUpdate = lineRepository.findByIdAndUser(lineId, user).orElseThrow(() ->
                new RuntimeException("Line not found"));

        if (lineRepository.findByTitleAndUser(title, user).isPresent()) {
            throw new RuntimeException("This title already exist: " + title);
        }
        lineToUpdate.setTitle(title);

        lineRepository.save(lineToUpdate);
    }

    public List<LineDTO> findLineList(Principal principal) {
        User user = findUserByPrincipal(principal);
        List<Line> lineList = lineRepository.findAllByUser(user);
        if (lineList.isEmpty()) {
            throw new RuntimeException("List is empty");
        }
        return entityMapper.mapLineListToLineListDTO(lineList);
    }

    public LineDTO findLineById(Long lineId, Principal principal) {
        User user = findUserByPrincipal(principal);

        Line line = lineRepository.findByIdAndUser(lineId, user).orElseThrow(() ->
                new RuntimeException("Line not found"));

        return entityMapper.mapLineToLineDTO(line);
    }

    private User findUserByPrincipal(Principal principal) {
        String email = principal.getName();

        return userRepository.findByEmail(email).orElse(null);
    }

}
