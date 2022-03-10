package com.example.thuongmai.model.user;

import com.example.thuongmai.model.comment.Comment;
import com.example.thuongmai.model.message.Message;
import com.example.thuongmai.model.notification.Notification;
import com.example.thuongmai.model.order.OrderProduct;
import com.example.thuongmai.model.role.Role;
import com.example.thuongmai.model.roomchat.RoomChat;
import com.example.thuongmai.model.shop.MyShop;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        })
})
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Lob
    @Column(columnDefinition = "varchar(255) default 'default-avatar.png'")
    private String avatar;
    private String email;
    private String phone;
    private Double wallet;
    private Double lockWallet;
    private String address;
    private LocalDate date;
    private String username;
    private String password;
    private Boolean status;
    @OneToMany(mappedBy = "user")
    private List<MyShop> shops;
    @OneToMany(mappedBy = "user")
    private List<Notification> notifications;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Message> messages;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<Comment> comments;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<OrderProduct> orderProducts;
    @JsonIgnore
    @OneToMany(mappedBy = "user")
    private List<RoomChat> roomChats;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "users_roles",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roles;

    public User(String name, String email, String username, String password) {
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
    }
}
