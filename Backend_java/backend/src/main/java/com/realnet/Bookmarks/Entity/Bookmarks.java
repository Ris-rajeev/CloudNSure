package com.realnet.Bookmarks.Entity;

import lombok.*;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.*;

@Entity
@Data
public class Bookmarks extends who_column {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String bookmark_firstletter;
	private String bookmark_link;
	private String fileupload_name;
	private String fileupload_path;

}
