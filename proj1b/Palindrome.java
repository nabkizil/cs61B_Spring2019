public class Palindrome {

    public Deque<Character> wordToDeque(String word) {

        Deque<Character> wordDeque = new ArrayDeque<>();
        for (char c : word.toCharArray()) {
            wordDeque.addLast(c);
        }
        return wordDeque;

    }

    /** Returns true if one word is a Palindrome. */
    public boolean isPalindrome(String word) {
        if (word.length() <= 1) {
            return true;
        }
        Deque d = wordToDeque(word);
        return isPalindromeHelper(d);
    }

    private boolean isPalindromeHelper(Deque d) {
        if (d.size() <= 1) {
            return true;
        } else if (d.removeFirst() == d.removeLast()) {
            return isPalindromeHelper(d);
        }
        return false;
    }

    /** Returns true if one word is a Palindrome according to CC, false otherwise. */
    public boolean isPalindrome(String word, CharacterComparator cc) {
        Deque d = wordToDeque(word);
        return isPalindrome(d, cc);
    }

    private boolean isPalindrome(Deque<Character> d, CharacterComparator cc) { //helper
        if (d.size() == 0 || d.size() == 1) { //base
            return true;
        }

        char first = d.removeFirst();
        char last = d.removeLast();
        return (cc.equalChars(first, last) && isPalindrome(d, cc)); //recursive
    }
}
