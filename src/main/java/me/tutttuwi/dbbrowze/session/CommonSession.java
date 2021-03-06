package me.tutttuwi.dbbrowze.session;

import java.io.Serializable;
import org.springframework.stereotype.Component;
import org.springframework.web.context.annotation.SessionScope;
import lombok.Data;
import me.tutttuwi.dbbrowze.dto.SidebarModel;

@Data
@Component
@SessionScope
public class CommonSession implements Serializable {
    private String username;
    private SidebarModel sidebarModel;
}
