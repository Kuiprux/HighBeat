package cf.kuiprux.spbeat.gui;

import org.newdawn.slick.Image;

public interface IHasTexture {
	TextureFillMode getTextureFillMode();
	Image getTexture();
	//texture �� null�Ͻ� �ؽ��� ����
	void setTexture(Image texture);
	void setTextureFillMode(TextureFillMode mode);
}
