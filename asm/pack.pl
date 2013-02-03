#!/usr/bin/perl

$DIR=shift(@ARGV);
print "Packing: $DIR\n";
$INDEX="${DIR}/pack.info";
if ( !open(INFO, "$INDEX") )
{
    print stderr "ERROR: Unable to open index file: $INDEX\n";
    exit 1;
}

$org = 0;
while ( $line = <INFO> )
{
    chomp($line);
    if ( $line =~ /^\s*$/ )
    {
	next;
    }
    elsif ( $line =~ /.org \$(\d+)/ )
    {
	$org = hex($1);
	printf "Origin: \$%04x\n", $org
    }
    else
    {
	$base = $line;
	$input = "$DIR/$base.asm";
	$output = "../build/classes/$DIR/$base.mob";
	print "$input ==> $output\n";
	$rc = system("echo '.org $org' > /tmp/mob");
	if ( $rc != 0 )
	{
	    print stderr "Unable to write origin to /tmp/mob\n";
	    exit 1;
	}
	$rc = system("cat $input >> /tmp/mob");
	if ( $rc != 0 )
	{
	    print stderr"Unable to append file to /tmp/mob\n";
	    exit 1;
	}
	$rc = system("/usr/local/bin/ophis -65c02 /tmp/mob $output");
	if ( $rc != 0 )
	{
	    print stderr "Compilation failed\n";
	    exit 1;
	}
	$fileSize = -s "$output";
	$end = $org + $fileSize - 1;
	printf("\$%04x - \$%04x\n", $org, $end);
	$org += $fileSize;
    }
}

