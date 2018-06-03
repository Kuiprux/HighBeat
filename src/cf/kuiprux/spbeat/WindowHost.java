package cf.kuiprux.spbeat;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.Game;
import org.newdawn.slick.SlickException;

public class WindowHost extends AppGameContainer implements AutoCloseable {
	
	//�⺻ â ũ��. ���� ����
	public static final int WIDTH = 600;
	public static final int HEIGHT = 800;
	
	public WindowHost(Game game) throws SlickException {
		this(game, WIDTH, HEIGHT);
	}
	
	public WindowHost(Game game, int width, int height) throws SlickException {
		super(game);
		
		init(width, height);
	}
	
	//���� �� â ���� ��� �ʱ�ȭ
	private void init(int width, int height) throws SlickException {
		setDisplayMode(width, height, false);
	}
	
	//���۽� ȣ��
	@Override
	public void start() throws SlickException {
		super.start();
	}

	//������ �ڵ� ȣ��
	@Override
	public void close() throws Exception {
		
	}

}
