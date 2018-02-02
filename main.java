public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener listener;

    private EditText lemail,lpassword,remail,rpassword;
    private Button but1,but2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lemail=(EditText)findViewById(R.id.le);
        remail=(EditText)findViewById(R.id.re);
        lpassword=(EditText)findViewById(R.id.lp);
        rpassword=(EditText)findViewById(R.id.rp);
        but1=(Button)findViewById(R.id.lb1);
        but2=(Button)findViewById(R.id.rb1);

        mAuth = FirebaseAuth.getInstance();
        listener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user != null){

                    Intent intent=new Intent(MainActivity.this , helloactivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }
            }
        };

        but2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = remail.getText().toString();
                String password = rpassword.getText().toString();
                mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                     if (!task.isSuccessful()){
                         Toast.makeText(MainActivity.this , "sign up error",Toast.LENGTH_SHORT ).show();
                     }
                    }
                });
            }
        });

        but1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = lemail.getText().toString();
                String password = lpassword.getText().toString();
                mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()){
                            Toast.makeText(MainActivity.this , "Login up error",Toast.LENGTH_SHORT ).show();
                        }
                    }
                });
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(listener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(listener);
    }
}
