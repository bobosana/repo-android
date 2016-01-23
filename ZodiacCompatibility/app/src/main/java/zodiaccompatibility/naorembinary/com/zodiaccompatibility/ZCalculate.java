package zodiaccompatibility.naorembinary.com.zodiaccompatibility;

import android.content.Context;

import java.util.Arrays;
import java.util.List;

/**
 * Created by niteen on 23-Jan-16.
 */
public class ZCalculate {
    private final String[][] chart= {
            {"Fiery","Very Erotic","Light Hearted","Many Differences","Intense","Hot and Cold", "Unstoppable","Very ardent","Adventurous","Lots of passion","Intriguing","Difficult"},
            {"Passionate","Well suited","Not a success","Caring and sharing","Extremely loving","Lots in common","Sensual","Quite magnetic","Very rocky","Terrific","Very strong","Charming"},
            {"Promising","A difficult combination","Could be fickle","Steer clear","Good fun","Erratic","Absolutely wonderful","Off key","Fun loving","Don't see eye to eye","Good friends","Traumatic"},
            {"Poor match","A cosy atmosphere","At odds","A dreamy affair","Emotionally straining","Stimulating company","Heavy going","Sparks fly","Troublesome","A caring couple","Upsetting","Highly charged"},
            {"Memorable","Truly emotional","Full of laughter","Loving","A difficult match","Great fun","A clash of Egos","Very powerful","Short but sweet","Can be difficult","Sparks fly"},
            {"Poles apart","Steady and enjoyable","Quite unusual","Problematic","Divided opinions","Can be boring","Hard going","Truly passionate","Different needs","In tune","Trouled waters","Heavenly"},
            {"Opposite attracts","Very sensual","Sublime","Hard work","Inigorating","Discordant","Too indecisive","Emotionally rewarding","Too flightly","Not a good bet","Marvellous match","Quite rewarding"},
            {"Long lasting","Rewarding relationship","Volatile","Paradise","Powerful attraction","Slow start","Heady stuff","Explosive","Can be strained","Entrancing","Up and down","Absolutely superb"},
            {"Wonderfully Romantic","Some confrontation","Hot and cold","Too distant","Truly passionate","Powerful","Sheer enjoyment","Chalk and cheese","Filed with excitement","Conflicts galore","Rather heated","Can be difficult"},
            {"May argue","Good prospects","Hard work","A great match","Very different","Can be strong","Not a winner","Very strong","Unsuitable","Long lasting","Tremendous","Sensual but erratic"},
            {"Very lively","Too stubborn","On the cool side","Too hurtful","Most entertaining","Not harmonious","Superlative","Many conflicts","Requires efforts","Can be tricky","Truly amazing","Can lead to tears"},
            {"Needs dedication","An excellent match","Needs insight","Quite entrancing","A romantic couple","Quite supportive","Fairytale romantic","Tantalising fun","Too many problems","Thoroughly passionate","Take care","Absolute bliss"}
    };
    private static String[] zMap={
                                "Aries",
                                "Taurus",
                                "Gemini",
                                "Cancer",
                                "Leo",
                                "Virgo",
                                "Libra",
                                "Scorpio",
                                "Sagittarius",
                                "Capricorn",
                                "Aquarius",
                                "Pisces"
                            };
public String zCalculate(Context c,String mZodiac,String fZodiac){
    String result;
    int mIndex,fIndex;
    mIndex= Arrays.asList(zMap).indexOf(mZodiac);
    fIndex= Arrays.asList(zMap).indexOf(fZodiac);
    result = chart[mIndex][fIndex];
    return result;
}
}
