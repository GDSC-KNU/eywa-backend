package kr.ac.knu.gdsc.Eywa.member.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import kr.ac.knu.gdsc.Eywa.common.domain.BaseTimeEntity;
import kr.ac.knu.gdsc.Eywa.member.dto.MemberDto;
import kr.ac.knu.gdsc.Eywa.register.domain.Register;
import kr.ac.knu.gdsc.Eywa.report.domain.Report;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@SequenceGenerator(
        name = "member_seq_generator",
        sequenceName = "member_seq",
        initialValue = 1,
        allocationSize = 1)
public class Member extends BaseTimeEntity {
    @JsonIgnore
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq")
    @Column(name = "member_id")
    private Long id;

    @NotNull
    private String sub;

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private int exp;

    @NotNull
    private String picture;

    @Enumerated(EnumType.STRING)
    @NotNull
    private Role role;

    @OneToOne
    @JoinColumn(name = "level_id")
    private Level level;

    @OneToMany(mappedBy = "member")
    private List<Register> registers = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Report> reports = new ArrayList<>();

    @Builder
    public Member(String sub, String name, String picture, String email, String role) {
        this.sub = sub;
        this.name = name;
        this.picture = picture;
        this.email = email;
        this.role = Role.valueOf(role);
    }

    // convert to dto
    public MemberDto toDto() {
        return MemberDto.builder()
                .name(this.name)
                .email(this.email)
                .exp(this.exp)
                .picture(this.picture)
                .level(this.level == null ? 0 : this.level.getLevel())
                .build();
    }
}