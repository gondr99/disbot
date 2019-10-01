package listener;

import domain.LunchVO;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class LunchListener extends ListenerAdapter {
	
	private Parser parser = new Parser();
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		String msg = event.getMessage().getContentRaw();
		
		if(msg.startsWith("!yy")) {
			//!yy lunch 20191001
			int idx = msg.indexOf(" "); //가장 처음으로 나오는 공백의 index
			if(idx < 0) {
				//event.getChannel().sendMessage("올바른 명령어를 입력하세요").queue();
				sayMsg(event, "올바른 명령어를 입력하세요");
				return;
			}
			//!yy 가 짤려나감.
			String cmd = msg.substring(idx + 1);
			int paramIdx = cmd.indexOf(" ");
			String param = "";
			if(paramIdx >= 0) {
				param = cmd.substring(paramIdx + 1);
				cmd = cmd.substring(0, paramIdx);
			}
			//if문이 끝나면 cmd에는 lunch 가 있고 param에는 20191001이 있게 된다.
			
			switch(cmd) {
			case "echo":
				if(param.isEmpty()) {
					sayMsg(event, "echo명령은 할 말을 입력해야 합니다.");
				}else {
					sayMsg(event, param);
				}
				break;
				
			case "lunch":
				//param이 올바른 값인지 검사하는 로직이 들어가야 해.
				
				LunchVO lunch = parser.getMenu(param);
				sayMsg(event, 
						lunch.getDate() + "의 메뉴는 " + lunch.getMenuString());
				break;
			case "help":
				String helpText = "skillbot의 명령은 다음과 같습니다.\n";
				helpText += "!yy echo [하고싶은 말] : 하고싶은 말을 봇이 따라합니다.\n";
				helpText += "!yy lunch [날짜 - 20190930 형식으로] : 해당 날짜의 급식을 가져옵니다.\n";
				
				sayMsg(event, helpText);
				break;
			default:
				sayMsg(event, "알 수 없는 명령입니다.");
			}
			
		}
	}
	
	private void sayMsg(MessageReceivedEvent e, String msg) {
		e.getChannel().sendMessage(msg).queue();
	}	
}





