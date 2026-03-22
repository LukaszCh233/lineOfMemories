package com.lineOfMemories.lineOfMemories.lineOfMemories.line;

import com.lineOfMemories.lineOfMemories.lineOfMemories.memory.Memory;
import com.lineOfMemories.lineOfMemories.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Line {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @OneToMany(mappedBy = "line", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Memory> memories = new ArrayList<>();
    private Visibility visibility;
}
