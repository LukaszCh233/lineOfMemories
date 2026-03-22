package com.lineOfMemories.lineOfMemories.mapper;

import com.lineOfMemories.lineOfMemories.admin.Admin;
import com.lineOfMemories.lineOfMemories.admin.AdminDTO;
import com.lineOfMemories.lineOfMemories.lineOfMemories.line.Line;
import com.lineOfMemories.lineOfMemories.lineOfMemories.line.LineDTO;
import com.lineOfMemories.lineOfMemories.user.User;
import com.lineOfMemories.lineOfMemories.user.UserDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class EntityMapper {
    public AdminDTO mapAdminToAdminDTO(Admin admin) {
        return new AdminDTO(admin.getName(), admin.getEmail());
    }

    public UserDTO mapUserToUserDTO(User user) {
        return new UserDTO(user.getName(), user.getEmail());
    }

    public LineDTO mapLineToLineDTO(Line line) {
        return new LineDTO(line.getTitle(), line.getUser().getUsername());
    }

    public List<LineDTO> mapLineListToLineListDTO(List<Line> lineList) {
        return lineList.stream()
                .map(this::mapLineToLineDTO)
                .collect(Collectors.toList());
    }
}
