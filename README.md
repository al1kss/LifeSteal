BUGS:

in Main.class change, so Namespaced key won't be deprecated
    
    
    private static Plugin plugin;
    
    public static Plugin getPlugin() {
        return instance;
    }

    public static void setPlugin(Plugin plugin) {
        Battlecraft.plugin = plugin;
    }
