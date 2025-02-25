PGDMP  )    )                }            superdia    17.0    17.0 )    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                           false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                           false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                           false            �           1262    41181    superdia    DATABASE        CREATE DATABASE superdia WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'Portuguese_Brazil.1252';
    DROP DATABASE superdia;
                     postgres    false            �            1259    41421    pessoa    TABLE     3  CREATE TABLE public.pessoa (
    datanascimento date NOT NULL,
    id bigint NOT NULL,
    cpf character varying(255) NOT NULL,
    email character varying(255) NOT NULL,
    endereco character varying(255) NOT NULL,
    nome character varying(255) NOT NULL,
    telefone character varying(255) NOT NULL
);
    DROP TABLE public.pessoa;
       public         heap r       postgres    false            �            1259    41420    pessoa_id_seq    SEQUENCE     v   CREATE SEQUENCE public.pessoa_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 $   DROP SEQUENCE public.pessoa_id_seq;
       public               postgres    false    218            �           0    0    pessoa_id_seq    SEQUENCE OWNED BY     ?   ALTER SEQUENCE public.pessoa_id_seq OWNED BY public.pessoa.id;
          public               postgres    false    217            �            1259    41475    produto    TABLE     �  CREATE TABLE public.produto (
    id bigint NOT NULL,
    descricao character varying(200),
    estoqueminimo integer NOT NULL,
    imageurl character varying(255),
    nome character varying(255) NOT NULL,
    preco double precision NOT NULL,
    quantidadeestoque integer NOT NULL,
    vendidopor character varying(255),
    CONSTRAINT produto_estoqueminimo_check CHECK ((estoqueminimo >= 0)),
    CONSTRAINT produto_quantidadeestoque_check CHECK ((quantidadeestoque >= 0))
);
    DROP TABLE public.produto;
       public         heap r       postgres    false            �            1259    41474    produto_id_seq    SEQUENCE     w   CREATE SEQUENCE public.produto_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.produto_id_seq;
       public               postgres    false    222            �           0    0    produto_id_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public.produto_id_seq OWNED BY public.produto.id;
          public               postgres    false    221            �            1259    41445    usuario    TABLE     �   CREATE TABLE public.usuario (
    perfil smallint NOT NULL,
    id bigint NOT NULL,
    pessoa_id bigint NOT NULL,
    senha character varying(255) NOT NULL,
    CONSTRAINT usuario_perfil_check CHECK (((perfil >= 0) AND (perfil <= 2)))
);
    DROP TABLE public.usuario;
       public         heap r       postgres    false            �            1259    41444    usuario_id_seq    SEQUENCE     w   CREATE SEQUENCE public.usuario_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.usuario_id_seq;
       public               postgres    false    220            �           0    0    usuario_id_seq    SEQUENCE OWNED BY     A   ALTER SEQUENCE public.usuario_id_seq OWNED BY public.usuario.id;
          public               postgres    false    219            �            1259    49377    venda    TABLE     y   CREATE TABLE public.venda (
    id bigint NOT NULL,
    usuario_id bigint NOT NULL,
    cartao character varying(255)
);
    DROP TABLE public.venda;
       public         heap r       postgres    false            �            1259    49376    venda_id_seq    SEQUENCE     u   CREATE SEQUENCE public.venda_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 #   DROP SEQUENCE public.venda_id_seq;
       public               postgres    false    224            �           0    0    venda_id_seq    SEQUENCE OWNED BY     =   ALTER SEQUENCE public.venda_id_seq OWNED BY public.venda.id;
          public               postgres    false    223            �            1259    49383    venda_produto    TABLE     e   CREATE TABLE public.venda_produto (
    venda_id bigint NOT NULL,
    produtos_id bigint NOT NULL
);
 !   DROP TABLE public.venda_produto;
       public         heap r       postgres    false            4           2604    41424 	   pessoa id    DEFAULT     f   ALTER TABLE ONLY public.pessoa ALTER COLUMN id SET DEFAULT nextval('public.pessoa_id_seq'::regclass);
 8   ALTER TABLE public.pessoa ALTER COLUMN id DROP DEFAULT;
       public               postgres    false    218    217    218            6           2604    41478 
   produto id    DEFAULT     h   ALTER TABLE ONLY public.produto ALTER COLUMN id SET DEFAULT nextval('public.produto_id_seq'::regclass);
 9   ALTER TABLE public.produto ALTER COLUMN id DROP DEFAULT;
       public               postgres    false    221    222    222            5           2604    41448 
   usuario id    DEFAULT     h   ALTER TABLE ONLY public.usuario ALTER COLUMN id SET DEFAULT nextval('public.usuario_id_seq'::regclass);
 9   ALTER TABLE public.usuario ALTER COLUMN id DROP DEFAULT;
       public               postgres    false    220    219    220            7           2604    49380    venda id    DEFAULT     d   ALTER TABLE ONLY public.venda ALTER COLUMN id SET DEFAULT nextval('public.venda_id_seq'::regclass);
 7   ALTER TABLE public.venda ALTER COLUMN id DROP DEFAULT;
       public               postgres    false    224    223    224            �          0    41421    pessoa 
   TABLE DATA           Z   COPY public.pessoa (datanascimento, id, cpf, email, endereco, nome, telefone) FROM stdin;
    public               postgres    false    218   /       �          0    41475    produto 
   TABLE DATA           u   COPY public.produto (id, descricao, estoqueminimo, imageurl, nome, preco, quantidadeestoque, vendidopor) FROM stdin;
    public               postgres    false    222   �/       �          0    41445    usuario 
   TABLE DATA           ?   COPY public.usuario (perfil, id, pessoa_id, senha) FROM stdin;
    public               postgres    false    220   �A       �          0    49377    venda 
   TABLE DATA           7   COPY public.venda (id, usuario_id, cartao) FROM stdin;
    public               postgres    false    224   �B       �          0    49383    venda_produto 
   TABLE DATA           >   COPY public.venda_produto (venda_id, produtos_id) FROM stdin;
    public               postgres    false    225   C       �           0    0    pessoa_id_seq    SEQUENCE SET     ;   SELECT pg_catalog.setval('public.pessoa_id_seq', 6, true);
          public               postgres    false    217            �           0    0    produto_id_seq    SEQUENCE SET     >   SELECT pg_catalog.setval('public.produto_id_seq', 128, true);
          public               postgres    false    221            �           0    0    usuario_id_seq    SEQUENCE SET     <   SELECT pg_catalog.setval('public.usuario_id_seq', 6, true);
          public               postgres    false    219            �           0    0    venda_id_seq    SEQUENCE SET     :   SELECT pg_catalog.setval('public.venda_id_seq', 7, true);
          public               postgres    false    223            <           2606    41430    pessoa pessoa_cpf_key 
   CONSTRAINT     O   ALTER TABLE ONLY public.pessoa
    ADD CONSTRAINT pessoa_cpf_key UNIQUE (cpf);
 ?   ALTER TABLE ONLY public.pessoa DROP CONSTRAINT pessoa_cpf_key;
       public                 postgres    false    218            >           2606    41432    pessoa pessoa_email_key 
   CONSTRAINT     S   ALTER TABLE ONLY public.pessoa
    ADD CONSTRAINT pessoa_email_key UNIQUE (email);
 A   ALTER TABLE ONLY public.pessoa DROP CONSTRAINT pessoa_email_key;
       public                 postgres    false    218            @           2606    41428    pessoa pessoa_pkey 
   CONSTRAINT     P   ALTER TABLE ONLY public.pessoa
    ADD CONSTRAINT pessoa_pkey PRIMARY KEY (id);
 <   ALTER TABLE ONLY public.pessoa DROP CONSTRAINT pessoa_pkey;
       public                 postgres    false    218            F           2606    41484    produto produto_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.produto
    ADD CONSTRAINT produto_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.produto DROP CONSTRAINT produto_pkey;
       public                 postgres    false    222            B           2606    41453    usuario usuario_pessoa_id_key 
   CONSTRAINT     ]   ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pessoa_id_key UNIQUE (pessoa_id);
 G   ALTER TABLE ONLY public.usuario DROP CONSTRAINT usuario_pessoa_id_key;
       public                 postgres    false    220            D           2606    41451    usuario usuario_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT usuario_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.usuario DROP CONSTRAINT usuario_pkey;
       public                 postgres    false    220            H           2606    49382    venda venda_pkey 
   CONSTRAINT     N   ALTER TABLE ONLY public.venda
    ADD CONSTRAINT venda_pkey PRIMARY KEY (id);
 :   ALTER TABLE ONLY public.venda DROP CONSTRAINT venda_pkey;
       public                 postgres    false    224            K           2606    49408 )   venda_produto fkedxrt0sld9dwmt5lfssjmhqvu    FK CONSTRAINT     �   ALTER TABLE ONLY public.venda_produto
    ADD CONSTRAINT fkedxrt0sld9dwmt5lfssjmhqvu FOREIGN KEY (venda_id) REFERENCES public.venda(id);
 S   ALTER TABLE ONLY public.venda_produto DROP CONSTRAINT fkedxrt0sld9dwmt5lfssjmhqvu;
       public               postgres    false    225    224    4680            L           2606    49403 )   venda_produto fkj6iuu680962oggsirha1h0v69    FK CONSTRAINT     �   ALTER TABLE ONLY public.venda_produto
    ADD CONSTRAINT fkj6iuu680962oggsirha1h0v69 FOREIGN KEY (produtos_id) REFERENCES public.produto(id);
 S   ALTER TABLE ONLY public.venda_produto DROP CONSTRAINT fkj6iuu680962oggsirha1h0v69;
       public               postgres    false    4678    222    225            J           2606    49398 !   venda fkkitm9mevclrw3div77bxnqjx5    FK CONSTRAINT     �   ALTER TABLE ONLY public.venda
    ADD CONSTRAINT fkkitm9mevclrw3div77bxnqjx5 FOREIGN KEY (usuario_id) REFERENCES public.usuario(id);
 K   ALTER TABLE ONLY public.venda DROP CONSTRAINT fkkitm9mevclrw3div77bxnqjx5;
       public               postgres    false    220    224    4676            I           2606    41454 #   usuario fkpb326uvtkob7eit0jbxona2q7    FK CONSTRAINT     �   ALTER TABLE ONLY public.usuario
    ADD CONSTRAINT fkpb326uvtkob7eit0jbxona2q7 FOREIGN KEY (pessoa_id) REFERENCES public.pessoa(id);
 M   ALTER TABLE ONLY public.usuario DROP CONSTRAINT fkpb326uvtkob7eit0jbxona2q7;
       public               postgres    false    4672    218    220            �   �   x�}�M
�@�u�^`$�8�d׃t�Zi����wThK.���H-�X���ǜA�驧[��+���i�����<��Xl`q>��b��#K50ԑ|d
Xe�K�0������W�YK���* �^�4r[������j ��UĪ��}�ݶeol���si�y�W�      �      x���r�ʚǯ�Sp��b�m�L��T����Rm�Yk֪��h$bZ�|�#�k̋�׀$t@��8{nE����}�M������$Aw��2��)*���$�f�D�,�K���\�vy��E1�q�td���2�U\�K˱�Kʙh����a�����E:����6u\f3�7*4`؄�̛ʰy��6oѤ2Lم�lY�y���w#+�/��8�b�����+\��ƽZ�%ݤ�Z$q ��*�ǩL�/�=�z �a������q�P�� �^���T�ȧ�^�}Őb8?2[bF5D�e9�Zd����Нz
f2N��,by����o��+�x���f��q���O(;���j�-p�f���߃esXY�����22@MZ�7�Q��>�:���B=��}3�!���s���\`�Ke�\�7�׍e�cm�u��qu����d���J�2���SJ��'��  ��4 ��A�(�(d��_�S�X�%
�Q�`�"��g*�����s;
(�q=��O�����*�z���]�ua���#�>���^����vΜ#��vCN���&�\��9/�ָ�_��:�0��L9L1?̘gЩe����rчUx�e��=�z����ps+�IE(������Z��\%�<�x��	��]b�V�j��ʬ�K�_Wf��<����J���A�Qwa�svP�<��E��2�Jȁ�eTe��R���c�d�>!�>�t��ߵ�8k��]�7s��A.*(��+���!�,�i"�B���NƷ�Z��d����8&��y����mvlN6f5�.��M�|I\��k�����4.�ŝ���h�{P~��[5;��<KK �Gǲ��G��qL}�G�P"�B�|�P$0�Qhc�|-.6�9�����y�'��[��0H�rsR��,OB�/S�I���T/_%��߯Gz<���6o�ys��ߵy�f�B����6�̳���*�t�e�RG Lh�m���Hg��T��j�:q�I;f�j�A%���_@�vG�ܪ���׫(}��x�=��#WE�ʬTg�f7����;������9�m�Cm˼[U�/v�l����N��)	_uV�#�^�Ƕ=[��Q�r�����^ՇE�uB��������p���p�\�>�*M;7�8�U-X�׻p��}�vv�OH�}�T= �K�(�PQ�4��>�;�}�71����̫\��6oNj��%�F��,����({��q׹d������A[��-o�n���4L�5�����8V�aA8�&A�oS����!2�帄˄eЪY�
%Tf@J�%�����%Ɲ�P���@�2��৲gfDa�2�hy���g3�"���2ζP��P�R�-�ac�6�q���r�PB��w� �����a�[)���w��`@.��(¡�Ba�3�l��+C��3u�E%iɤP�ۖqմj���Jle�`V[�aZ�G�AL~i�������-h!����zj.��dA��ܘ&��J����7m� �l%l(�1u�8B>!
��D�+� Q�u��gh����-M�{�g�X�]��6G�.[��<*dk�tb�^o?�H�jd�=�KO��*��;OM���q��"H��I	5No�C,½�2E[h��l�x��̘ ��fh����mY1�T9M�M�)� n��P)9��i���g��C~�x�+C0˽�m ��cR���=���8����fj~��L�s濚c���f���>@�-�9(�jY�8��ײ���i�'Y2��lNj���O����6���u��@���z��tH��#XY�l~|�t��q��贇�FçY�𓾎��y�"����M@+���X��K���r�x���B�(�|GVC�r�a�؁�'}�s'��@'��dXTb(�[��ƌ9�����+z�zM���l�HF�T��\��K����>sn�}���k����d�e�n�8.�
����'2`"R$�d�C�l�kNs ��*��HH���B�]��r��\�%�ďqߴ{'Ҝ��DNoPe�0�eD�EG"!���b��� c����Z':���9]�-�Ѻ̫JW�l�v��ةN�o��E�odi�dһ�xfa/��JBdq7@��-)�/��BVH�owa��kM�Xk����Tu[~�����ҋs
)�q�2������E��ˠ��1�_?�C_'!އ���چ�E�ʯB�.,F�0�d�77ϛ�ba0��Yב�� ���x:±������C|�/������m�A�cM���t���F�ˇ����"[�~��×�/�����}Ȓj��n�@|�iY�q��Q�z׾5�N�\B���_�8�5DQ��*���76��F#�Ц(u,����@ؿX!�8~)�.�54���׈�'Mu�&��BEfNj�v�˂7��r{����k[56���+�Y�������Ng�E(�X�@�>�~����Ha��́CO� Q2-���J��[������VM�\w�0��1<:��p������� D�"p"�UV�G��rs�'���������]� Z��j5G<r�d߽Qc��(�%|�."�)�@.#>�čB�B�62(%�0V-�ڎ��[����vGR����-4;�߃�Z8,��wS��4 цs���&���V�Qm�� N�Y<��4�����u����$�Õ'�S���� ��kS���Sh�ږ�z����S�- m''�AW�y��S� �9���J���U[����dRo�d0��q���\͠�6��l����4Qr�B�2����ڔ#��S�4��G-Wa�6c��%��Z�c��lm�M��u*Z~�nG���"���}�:���imzS�܎l\��2-�vM�7��&��Qդw?=Gz�γ����)
��uVi#WEّ�݈��
������M��n׬���nΪ8��^N$!���nl��u�׺�C�"��M !���r׵��QS�h]�P����f�䣘U;O:�_�n��-�6-��sJ��]�r�m@�gH*J��"pl����[�}��ͦ�Z�h�U����	�X��-�~N��=�i���7F�!�C����_��|�<d�o`�<.k��� ?��.9��'\qb�oR�eվ�S���:�&�C�4�˸��DM���T\J��4�����f�Ƚ��cȽ2�"U�eQ����,���{	1��Di|���a�dA��mY�(�P�P��¡.!���S)4W�A8�����r��w�|#C��z���[��ׁQ�i��;���
�a��x���j�gY�Ms��=y��0�o�z��NZ@GAY�.�M�6��[�.��d�3}a�;g��#���-b����	�/�B��-��h�~�Ͷ}��]0�r��l:-����"�8�%�ÖsDܸ��E��d;'��34�9{�G�:�t�@�c�r%�k�u�sO}��S��4x�-ȟ�*�C����P%�y@�p�.u�9Sw�*�SƘ��G���me��Bߠ:ynD�郇Y��+z+ɴ��y�T����J�M�(������6��bq���DE�������4���]=z��a����Ċb;.�*�ۻp.���#�u�1?֚1��2v��@
H>@z�#&���G]�P鷦�+ڮ88!�$�|���)v��ne�\�E-pu��� �����6d�B�Đ�U�W�3V�`���-N��H���J搷�SR�#R��S�zj�}Ƿ�DƎ{B��q��s�.Q43z}}���R�n*����D����CM��?ai�]"V�-e�_����s��<�����ds-���7��K9+Q���#A�}	D@UyB�o��!���8k�p~�l��dW��u}Π�)}��	Z���!]��r+s��t,�Bo��$P���-L�^�µE���Ց���O�eų:�x`���y\��%+$&�� �Ϋ�]�|p� 8>����d��?ӵ.�$��]�f�����]� a�[�w2ũ��� (��7O��>E�_b@X�� �  ����a�H:�@Y�q�G��s��P��RT���@J	9%pPA�]��?:�A|�u��Ud�����9H����9u����j��|��������JXн�綊���ň��'� A����Bm���3d�7Oj�]W�� }�2qg�sf��gn5}����<�X�>d���	5���� �ЈR�Uû���`��ә/�������n�b��Nx����dߣ�'����s��m�('��"����g�}������Q��{�c�x皷ȏ�&�����cT/@��,���,�2����n����u_����_~�I��{��ޫ���-�������f`���w3�%~���:|��繝��������?�����g���1��'oh`|�z�?�}x������M�ȗ40�?�-��/� ۳@�<w50�_�e����������k`b���50����&����	}�W60a?ƝL�Oui��nm`b�S\������m��ś7o�	�~      �   �   x�5��r�0 @ѵ�����*�N7���Ph������ߙ���+A�[I �i��l7��?N-�8>[��\��%޲��OVd���C�D�7��;���tL�*]�:w7<(�x�R��d����<K?E��g��Hu�N��"z�	�/�45�PP��y̭FuZ��-Z���7�C]��5���G�,UT=�"�j��Ro8=Ԓ;Gh�2HH�'���9�̞F�� Y�P�      �   7   x�m�1  ����B��� ��w��I�\�CD�r�r�Tp&��öp:��s�      �      x�3�4�2�4�2��2�́�=... )\     