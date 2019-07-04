package Models;


import com.dighisoft.christocentric.AppDatabase;
import com.dighisoft.christocentric.BaseApplication;
import com.reactiveandroid.Model;
import com.reactiveandroid.annotation.Column;
import com.reactiveandroid.annotation.OneToMany;
import com.reactiveandroid.annotation.PrimaryKey;
import com.reactiveandroid.annotation.Table;
import com.reactiveandroid.query.Select;

import java.util.List;

@Table(name = "USER" ,database = AppDatabase.class)
public class UserDBModel  extends Model {

    @PrimaryKey
    public Long userid;
    @Column(name = "jwt")
    public String jwt;
//    @Column(name = "user")
//    public Long userid;
    @Column(name = "church")
    public int church;
//    @Column(name = "username")
//    public int username;

    public static List<UserDBModel> getUser(){

        /*
        * ActiveAndroid Library
        * Select Class helps to create the SQL like statements
        * */

        return Select.from(UserDBModel.class).fetch();
    }

//   public static List<BranchDatabase>getBranches(){
//        return Select.
//   }
}
