package dsa;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

class MerkleNode {
    String hash;
    MerkleNode left, right;

    MerkleNode(String hash) { this.hash = hash; }
}

class ProofElement {
    String hash;
    boolean isLeft;

    ProofElement(String hash, boolean isLeft) {
        this.hash = hash;
        this.isLeft = isLeft;
    }

    @Override
    public String toString() {
        return "ProofElement{hash='" + hash + "', position='" + (isLeft ? "left" : "right") + "'}";
    }
}

public class MerkleTree {
    private MerkleNode root;
    private List<MerkleNode> leaves;

    public MerkleTree(List<String> data) {
        if (data.isEmpty()) throw new IllegalArgumentException("Data list cannot be empty");
        leaves = new ArrayList<>();
        for (String item : data) leaves.add(new MerkleNode(calculateHash(item)));
        if (leaves.size() % 2 == 1) leaves.add(leaves.get(leaves.size() - 1));
        buildTree();
    }

    private String calculateHash(String data) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            return Base64.getEncoder().encodeToString(digest.digest(data.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new RuntimeException("Hash calculation error", e);
        }
    }

    private void buildTree() {
        List<MerkleNode> currentLevel = leaves;
        while (currentLevel.size() > 1) {
            List<MerkleNode> nextLevel = new ArrayList<>();
            for (int i = 0; i < currentLevel.size(); i += 2) {
                MerkleNode left = currentLevel.get(i);
                MerkleNode right = (i + 1 < currentLevel.size()) ? currentLevel.get(i + 1) : left;
                nextLevel.add(new MerkleNode(calculateHash(left.hash + right.hash)));
            }
            currentLevel = nextLevel;
        }
        root = currentLevel.get(0);
    }

    public String getRootHash() {
        return root.hash;
    }

    public List<ProofElement> generateProof(int index) {
        if (index >= leaves.size()) throw new IllegalArgumentException("Index out of bounds");
        List<ProofElement> proof = new ArrayList<>();
        List<MerkleNode> currentLevel = leaves;

        while (currentLevel.size() > 1) {
            List<MerkleNode> nextLevel = new ArrayList<>();
            for (int i = 0; i < currentLevel.size(); i += 2) {
                MerkleNode left = currentLevel.get(i);
                MerkleNode right = (i + 1 < currentLevel.size()) ? currentLevel.get(i + 1) : left;

                if (i == index) proof.add(new ProofElement(right.hash, false));
                else if (i + 1 == index) proof.add(new ProofElement(left.hash, true));

                nextLevel.add(new MerkleNode(calculateHash(left.hash + right.hash)));
            }
            index /= 2;
            currentLevel = nextLevel;
        }
        return proof;
    }

    public boolean verifyProof(String data, List<ProofElement> proof) {
        String currentHash = calculateHash(data);
        for (ProofElement element : proof) {
            currentHash = calculateHash((element.isLeft ? element.hash + currentHash : currentHash + element.hash));
        }
        return currentHash.equals(getRootHash());
    }


    public static void main(String[] args) {
        List<String> transactions = List.of("Transaction1", "Transaction2", "Transaction3", "Transaction4");
        MerkleTree merkleTree = new MerkleTree(transactions);
        System.out.println("Root Hash: " + merkleTree.getRootHash());

        int testIndex = 2; // Verify Transaction3
        List<ProofElement> proof = merkleTree.generateProof(testIndex);
        System.out.println("Proof: " + proof);
        System.out.println("Proof verification: " + merkleTree.verifyProof(transactions.get(testIndex), proof));
        System.out.println("Verification with incorrect data: " + merkleTree.verifyProof("InvalidTransaction", proof));
    }
}