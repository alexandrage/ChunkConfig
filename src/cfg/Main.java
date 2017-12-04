package cfg;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
	private BlockTagMap saves;

	@Override
	public void onEnable() {
		this.saves = new BlockTagMap(this);
		getServer().getPluginManager().registerEvents(new EventListener(this.saves), this);
		new Scheduler(this.saves).runTaskTimer(this, 12000, 12000);
		this.getDataFolder().mkdirs();
	}

	@Override
	public void onDisable() {
		this.saves.Save();
	}
}