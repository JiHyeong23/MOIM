package MOIM.svr.security;

import lombok.Setter;

@Setter
public class Token {
    private String access;
    private String refresh;
}
