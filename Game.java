package text1;

import java.util.HashMap;
import java.util.Scanner;

public class Game {
    private Room currentRoom;
    public boolean Artifactget =false;
    static Game game = new Game();
    HashMap<String ,Handler> handlers = new HashMap<String, Handler>();
   
    private class Handler {
    	public void  doCmd(String work){}	
    	public boolean isBey(){return false;}
    }
   
    public Game() //game的构造器
    {
        
        handlers.put("bey" , new Handler(){
        	@Override
        	public boolean isBey() {
        		return true;
        	}
        });
        handlers.put("help", new Handler(){
        	@Override
        	public void doCmd(String work) {
        		System.out.println("想打败boss你必须先找到神器");
        		System.out.print("迷路了吗？你可以做的命令有：go bye help take ");
                System.out.println("如：\tgo east");
        		
        	}
        });
        handlers.put("go", new Handler(){
        	@Override
        	public void doCmd(String word) {
        		game.goRoom(word);
        	}
        	
        });
        handlers.put("take", new Handler(){
        	@Override
        	public void doCmd(String work) {
        		super.doCmd(work);
        		game.takeArtifact();
        		System.out.println("你拿起了神器！");
        	}
        });
        createRooms();
    }

    private void createRooms()
    {
        Room outside, lobby, pub, study, bedroom, weaponsroom,bossroom;//创造了房间的对象
      
        //	制造房间
        outside = new CommonRoom("城堡外");//调用有string参数的构造器
        lobby = new CommonRoom("大堂");
        pub = new CommonRoom("小酒吧");
        study = new CommonRoom("书房");
        bedroom = new RewardRoom("卧室");
        weaponsroom= new RewardRoom("武器室");
        bossroom=new MonsterRoom("bossroom");
        
         //初始化房间里的元素
        outside.setthing(outside,null);
        lobby.setthing(lobby,null);
        pub.setthing(pub,null);
        study.setthing(study,null);
        bedroom.setthing(bedroom,"公主");
        weaponsroom.setthing(weaponsroom,"神器");
        bossroom.setthing(bossroom,"boss");
        
        //	初始化房间的出口
        outside.setExit("east",lobby);
        outside.setExit("south",study);
        outside.setExit("west",pub);
        lobby.setExit("west", outside);
        pub.setExit("east", outside);
        study.setExit("north", outside);
        study.setExit("east", bossroom);
        study.setExit("south", weaponsroom);
        weaponsroom.setExit("north", study);
        bossroom.setExit("west", study);
        bossroom.setExit("east", weaponsroom);
        

        currentRoom = outside;  //	从城堡门外开始
    }
    public void play(){
    	Scanner in = new Scanner(System.in); 
    	 while ( true ) {
     		String line = in.nextLine();
     		String[] words = line.split(" ");
     		Handler handler=handlers.get(words[0]);//把子类对象赋给父类，向上转型
     		String value ="";
     		if(words.length>1){
     			value=words[1];
     		}
     		if(handler!=null){
     			handler.doCmd(value);
     			if(handler.isBey()){
     				System.out.println("感谢您的光临，再见！");
     				break;
     				}
     		}
     		
     }
    	 in.close();
    }
    
	public void takeArtifact(){
    	Artifactget=true;

    }
    private void printWelcome() {//运行开始的输出
        System.out.println();
        System.out.println("欢迎来到城堡！");
        System.out.println("这是一个营救公主的游戏。");
        System.out.println("公主正在危难之中！");
        System.out.println("打败boss，救出公主才能取得游戏胜利");
        System.out.println("如果需要帮助，请输入 'help' 。");
        System.out.println();
        showPrompt();
    }
    public void showPrompt(){
  	  System.out.println("现在你在" + currentRoom);
  	  if(currentRoom.getthing(currentRoom)==null){
  		  System.out.println("这里没有可拾取物品！");
  	  }else
  	  System.out.println("这里有"+currentRoom.getthing(currentRoom));
  	  if(currentRoom.toString()=="bossroom"){
  		  if(Artifactget==false){
  			  System.out.println("你没有神器打不过boss！");
  		  }else{
  			  System.out.println("你打死了boss，游戏胜利,你可以继续游玩城堡。");
  		  }
  	  }
        System.out.print("出口有："+currentRoom.getExitDesc());
        
        System.out.println();
  }
   
    // 以下为用户命令
   
   

    public  void goRoom(String direction) 
    {
        Room nextRoom = currentRoom.getExit(direction);
       
        if (nextRoom == null) {
            System.out.println("那里没有门！");
        }
        else {
            currentRoom = nextRoom;
            showPrompt();
        }
    }
	
	public static void main(String[] args) {
		
    	//创建一个game对象
    	game.printWelcome();
    	game.play();
    	
      
       
	}

}
